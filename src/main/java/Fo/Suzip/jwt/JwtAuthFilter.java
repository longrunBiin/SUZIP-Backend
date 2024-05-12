package Fo.Suzip.jwt;

import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.exception.handler.TokenHandler;
import Fo.Suzip.converter.MemberConverter;
import Fo.Suzip.domain.RefreshToken;
import Fo.Suzip.repository.RefreshTokenRepository;
import Fo.Suzip.web.dto.authDTO.CustomOAuth2User;
import Fo.Suzip.web.dto.authDTO.SecurityUserDto;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
//@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String atc = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());

                if (cookie.getName().equals("Authorization")) {
                    atc = cookie.getValue();
                }
            }
        }
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            header = header.substring(7);
            atc = header;
        }
        System.out.println("header = " + header);
        System.out.println("atc = " + atc);
        if (atc == null && !StringUtils.hasText(header)) {
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }



        // AccessToken을 검증하고, 만료되었을경우 재발급한다.
        if (!jwtUtil.verifyToken(atc)) {
            System.out.println("만료");
            RefreshToken refreshToken = tokenRepository.findByAccessToken(atc)
                    .orElseThrow(() -> new TokenHandler(ErrorStatus._TOKEN_UNSUPPORTED));

            // RefreshToken이 존재하고 유효하다면 실행
            if (jwtUtil.verifyToken(refreshToken.getRefreshToken())) {
                String newAccessToken = jwtUtil.generateAccessToken(refreshToken.getUsername(), jwtUtil.getRole(refreshToken.getRefreshToken()));
                System.out.println("atc = " + atc);
                System.out.println("refreshToken.getAccessToken = " + refreshToken.getAccessToken());
                System.out.println("newAccessToken = " + newAccessToken);
                // 액세스 토큰의 값을 수정해준다.
                refreshToken.updateAccessToken(newAccessToken);

                System.out.println("재발급");
                response.addCookie(createCookie("Authorization", newAccessToken));
            }

        }
        // AccessToken의 값이 있고, 유효한 경우에어떻게  진행한다.
        if (jwtUtil.verifyToken(atc)) {
            // SecurityContext에 등록할 User 객체를 만들어준다.
            SecurityUserDto userDto = MemberConverter.toSecurityUserDto(jwtUtil, atc);
            //UserDetails에 회원 정보 객체 담기
            CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);
            //스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
            // SecurityContext에 인증 객체를 등록해준다.
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
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