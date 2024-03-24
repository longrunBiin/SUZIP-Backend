package Fo.Suzip.jwt;

import Fo.Suzip.service.RefreshTokenService;
import Fo.Suzip.web.dto.GeneratedToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;
    private final RefreshTokenService tokenService;
    private SecretKey secretKey;


    @PostConstruct
    protected void init() {
        byte[] apiKeySecretBytes = Base64.getEncoder().encode(jwtProperties.getSecret().getBytes());
        secretKey = Keys.hmacShaKeyFor(apiKeySecretBytes);
        System.out.println("secretKey = " + secretKey);
    }


    public GeneratedToken generateToken(String email, String role) {
        System.out.println("JwtUtil.generateToken");
        // refreshToken과 accessToken을 생성한다.
        String refreshToken = generateRefreshToken(email, role);
        String accessToken = generateAccessToken(email, role);

        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        // 토큰을 Redis에 저장한다.
        tokenService.saveTokenInfo(email, refreshToken, accessToken);
        return new GeneratedToken(accessToken, refreshToken);
    }

    public String generateRefreshToken(String email, String role) {
        // 토큰의 유효 기간을 밀리초 단위로 설정.
        long refreshPeriod = 1000L * 60L * 60L * 24L * 14; // 2주

        // 새로운 클레임 객체를 생성하고, 이메일과 역할(권한)을 셋팅
        Claims claims = Jwts.claims().subject(email).add("role", role).build();


        // 현재 시간과 날짜를 가져온다.
        Date now = new Date();

        return Jwts.builder()
                // Payload를 구성하는 속성들을 정의한다.
                .claims(claims)
                // 발행일자를 넣는다.
                .issuedAt(now)
                // 토큰의 만료일시를 설정한다.
                .expiration(new Date(now.getTime() + refreshPeriod))
                // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
                .signWith(secretKey)
                .compact();
    }


    public String generateAccessToken(String email, String role) {
        long tokenPeriod = 1000L * 60L * 30L; // 30분
        Claims claims = Jwts.claims().subject(email).add("role", role).build();
        Date now = new Date();
        return
                Jwts.builder()
                        // Payload를 구성하는 속성들을 정의한다.
                        .claims(claims)
                        // 발행일자를 넣는다.
                        .issuedAt(now)
                        // 토큰의 만료일시를 설정한다.
                        .expiration(new Date(now.getTime() + tokenPeriod))
                        // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
                        .signWith(secretKey)
                        .compact();

    }


    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKey) // 비밀키를 설정하여 파싱한다.
                    .build()
                    .parseSignedClaims(token);  // 주어진 토큰을 파싱하여 Claims 객체를 얻는다.
            // 토큰의 만료 시간과 현재 시간비교
            System.out.println("claims.getPayload() = " + claims.getPayload());
            return claims.getPayload()
                    .getExpiration()
                    .after(new Date());  // 만료 시간이 현재 시간 이후인지 확인하여 유효성 검사 결과를 반환
        } catch (Exception e) {
            return false;
        }
    }


    // 토큰에서 Email을 추출한다.
    public String getUid(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    // 토큰에서 ROLE(권한)만 추출한다.
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }
    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getEmail(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }
    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String getProvider(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("provider", String.class);
    }

    public String getName(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("name", String.class);
    }
}