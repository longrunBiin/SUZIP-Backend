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

        System.out.println("atc = " + atc);
        if (atc == null) {
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

//        String atc = request.getHeader("Authorization");
//        System.out.println("atc = " + atc);
//        // 토큰 검사 생략(모두 허용 URL의 경우 토큰 검사 통과)
//        if (!StringUtils.hasText(atc)) {
//            System.out.println("JwtAuthFilter.doFilterInternal");
//            doFilter(request, response, filterChain);
//            return;
//        }
        // AccessToken을 검증하고, 만료되었을경우 예외를 발생시킨다.
        if (!jwtUtil.verifyToken(atc)) {
            RefreshToken refreshToken = tokenRepository.findByAccessToken(atc)
                    .orElseThrow(() -> new TokenHandler(ErrorStatus._TOKEN_UNSUPPORTED));

            // RefreshToken이 존재하고 유효하다면 실행
            if (jwtUtil.verifyToken(refreshToken.getRefreshToken())) {
                String newAccessToken = jwtUtil.generateAccessToken(refreshToken.getUsername(), jwtUtil.getRole(refreshToken.getRefreshToken()));
                System.out.println("atc = " + atc);
                System.out.println("newAccessToken = " + newAccessToken);
                // 액세스 토큰의 값을 수정해준다.
                refreshToken.updateAccessToken(newAccessToken);
                tokenRepository.save(refreshToken);
                System.out.println("재발급");

            }

        }
        // AccessToken의 값이 있고, 유효한 경우에 진행한다.
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

}