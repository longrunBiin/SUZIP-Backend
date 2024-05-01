package Fo.Suzip.apiPayload.exception.handler;

import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.web.dto.authDTO.CustomOAuth2User;
import Fo.Suzip.web.dto.authDTO.GeneratedToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

//@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Value("${BASE_URI}")
    private String baseUri;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("MyAuthenticationSuccessHandler.onAuthenticationSuccess");
        // OAuth2User로 캐스팅하여 인증된 사용자 정보를 가져온다.
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        // 사용자 이메일을 가져온다.
        String email = oAuth2User.getEmail();
        String username = oAuth2User.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        GeneratedToken token = jwtUtil.generateToken(username, role);
        response.addCookie(createCookie("Authorization", token.getAccessToken()));
//        response.setHeader("Authorization", "Bearer " + token.getAccessToken());
        System.out.println("redirect");
        String targetUrl = UriComponentsBuilder.fromUriString("http://"+baseUri+":3000/")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
//        log.info("redirect 준비");
        // 로그인 확인 페이지로 리다이렉트 시킨다.
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private Cookie deleteCookie(String key) {

        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}