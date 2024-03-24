package Fo.Suzip.jwt;

import Fo.Suzip.domain.Member;
import Fo.Suzip.repository.MemberRepository;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.web.dto.CustomOAuth2User;
import Fo.Suzip.web.dto.SecurityUserDto;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request Header에서 AccessToken을 가져온다.
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

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        System.out.println("JwtAuthFilter.doFilterInternal1");
        // AccessToken을 검증하고, 만료되었을경우 예외를 발생시킨다.
        if (!jwtUtil.verifyToken(atc)) {
            throw new JwtException("Access Token 만료!");
        }
        // AccessToken의 값이 있고, 유효한 경우에 진행한다.
        if (jwtUtil.verifyToken(atc)) {

            System.out.println("jwtUtil = " + jwtUtil.getProvider(atc));
            // SecurityContext에 등록할 User 객체를 만들어준다.
            SecurityUserDto userDto = SecurityUserDto.builder()
                    .id(jwtUtil.getUid(atc))
                    .email(jwtUtil.getEmail(atc))
                    .role(jwtUtil.getRole(atc))
                    .userName(jwtUtil.getUsername(atc))
                    .name(jwtUtil.getName(atc))
                    .build();


            //UserDetails에 회원 정보 객체 담기
            CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDto);

            //스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
            //세션에 사용자 등록

            // SecurityContext에 인증 객체를 등록해준다.
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

}