package Fo.Suzip.apiPayload.exception.handler;

import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.web.dto.CustomOAuth2User;
import Fo.Suzip.web.dto.GeneratedToken;
import Fo.Suzip.web.dto.MemberRequestDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("MyAuthenticationSuccessHandler.onAuthenticationSuccess");
        // OAuth2User로 캐스팅하여 인증된 사용자 정보를 가져온다.
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // 사용자 이메일을 가져온다.
        String email = oAuth2User.getEmail("email");

        // 서비스 제공 플랫폼(GOOGLE, KAKAO, NAVER)이 어디인지 가져온다.
//        String provider = oAuth2User.getAttribute("provider");
//
//        String username = oAuth2User.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        GeneratedToken token = jwtUtil.generateToken(email, role);

        response.addCookie(createCookie("Authorization", token.getAccessToken()));
        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/home")
                .queryParam("accessToken", token.getAccessToken())
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
        log.info("redirect 준비");
        // 로그인 확인 페이지로 리다이렉트 시킨다.
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

//
//        // CustomOAuth2UserService에서 셋팅한 로그인한 회원 존재 여부를 가져온다.
//        boolean isExist = Boolean.TRUE.equals(oAuth2User.getAttribute("exist"));
//        // OAuth2User로 부터 Role을 얻어온다.
//        String role = oAuth2User.getAuthorities().stream().
//                findFirst() // 첫번째 Role을 찾아온다.
//                .orElseThrow(IllegalAccessError::new) // 존재하지 않을 시 예외를 던진다.
//                .getAuthority(); // Role을 가져온다.
//
//        // 회원이 존재할경우
//        if (isExist) {
//            // 회원이 존재하면 jwt token 발행을 시작한다.
//            GeneratedToken token = jwtUtil.generateToken(email, role);
//            log.info("jwtToken = {}", token.getAccessToken());
//            System.out.println("token = " + token);
//            // accessToken을 쿼리스트링에 담는 url을 만들어준다.
//            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/home")
//                    .queryParam("accessToken", token.getAccessToken())
//                    .build()
//                    .encode(StandardCharsets.UTF_8)
//                    .toUriString();
//            log.info("redirect 준비");
//            // 로그인 확인 페이지로 리다이렉트 시킨다.
//            getRedirectStrategy().sendRedirect(request, response, targetUrl);
//
//
//        } else {
//            MemberRequestDTO.JoinDto mem = new MemberRequestDTO.JoinDto();
//            mem.setEmail((String) oAuth2User.getAttribute("email"));
//            mem.setProvider(provider);
//            mem.setRole("ROLE_USER");
//            memberService.signup(mem);
//
//            // 회원이 존재하지 않을경우, 서비스 제공자와 email을 쿼리스트링으로 전달하는 url을 만들어준다.
//            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/signUp")
//                    .queryParam("email", (String) oAuth2User.getAttribute("email"))
//                    .queryParam("provider", provider)
//                    .build()
//                    .encode(StandardCharsets.UTF_8)
//                    .toUriString();
//
//            System.out.println("targetUrl = " + targetUrl);
//            // 회원가입 페이지로 리다이렉트 시킨다.
//            getRedirectStrategy().sendRedirect(request, response, targetUrl);
//        }
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}