package Fo.Suzip.apiPayload.exception.handler;

import Fo.Suzip.jwt.JWTUtil;
import Fo.Suzip.web.dto.user.CustomOAuth2USer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    public CustomSuccessHandler(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        //OAuth2User
//        CustomOAuth2USer customUserDetails = (CustomOAuth2USer) authentication.getPrincipal();
//
//        String username = customUserDetails.getUsername();
//
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority auth = iterator.next();
//        String role = auth.getAuthority();
//
//        String token = jwtUtil.createJwt(username, role, 60*60*60L);
//
//        response.addCookie(createCookie("Authorization", token));
//        response.sendRedirect("http://localhost:8080/home");
//    }
//
//    private Cookie createCookie(String key, String value) {
//
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60*60*60);
//        //cookie.setSecure(true);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//
//        return cookie;
//    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2USer oAuth2User = (CustomOAuth2USer) authentication.getPrincipal();

        String email = oAuth2User.getEmail(); // OAuth2User로부터 Resource Owner의 이메일 주소를 얻음 객체로부터
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        List<String> authorities = authorityUtils.createRoles(email);           // 권한 정보 생성
        authorities.stream().map(authoritie -> authoritie.replaceFirst("a", "")).collect(Collectors.toList());

        redirect(request, response, email, authorities);  // Access Token과 Refresh Token을 Frontend에 전달하기 위해 Redirect
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String email, List<String> authorities) throws IOException {
        log.info("Token 생성 시작");
        String accessToken = delegateAccessToken(email, authorities);  // Access Token 생성
        String refreshToken = delegateRefreshToken(email);     // Refresh Token 생성
        User user = userService.findByEmail(email);
        Long userId = user.getUserId();
        String username = user.getUsername();
        user.setRefreshToken(refreshToken);
        userService.saveUser(user);

        String uri = createURI(accessToken, refreshToken, userId, username).toString();   // Access Token과 Refresh Token을 포함한 URL을 생성
        getRedirectStrategy().sendRedirect(request, response, uri);   // sendRedirect() 메서드를 이용해 Frontend 애플리케이션 쪽으로 리다이렉트
    }

    // Access Token 생성
    private String delegateAccessToken(String username, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("roles", authorities);

        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    // Refresh Token 생성
    private String delegateRefreshToken(String username) {
        String subject = username;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    // Redirect URI 생성. JWT를 쿼리 파라미터로 담아 전달한다.
    private URI createURI(String accessToken, String refreshToken, Long userId, String username) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("user_id", String.valueOf(userId));
        queryParams.add("username", username);
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("66challenge.shop")
                .path("/oauth")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
}