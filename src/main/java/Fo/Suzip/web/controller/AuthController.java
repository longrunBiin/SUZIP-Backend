package Fo.Suzip.web.controller;

import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.apiPayload.code.status.ErrorStatus;
import Fo.Suzip.apiPayload.code.status.SuccessStatus;
import Fo.Suzip.converter.JwtConverter;
import Fo.Suzip.converter.MemberConverter;
import Fo.Suzip.domain.Member;
import Fo.Suzip.domain.RefreshToken;
import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.repository.RefreshTokenRepository;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.service.RefreshTokenService;
import Fo.Suzip.web.dto.authDTO.GeneratedToken;
import Fo.Suzip.web.dto.authDTO.StatusResponseDto;
import Fo.Suzip.web.dto.memberDTO.MemberRequestDTO;
import Fo.Suzip.web.dto.memberDTO.MemberResponseDTO;
import ch.qos.logback.core.rolling.helper.TokenConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
//
    private final RefreshTokenRepository tokenRepository;
    private final RefreshTokenService tokenService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @GetMapping("/auth/status")
    public ApiResponse<MemberResponseDTO.JoinResultDto> getStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();

        if (isAuthenticated) {
            String userName = authentication.getName();

            Member member = memberService.findMemberById(userName);
            RefreshToken token = tokenService.getAccessToken(member);
            return ApiResponse.onSuccess(MemberConverter.toJoinResult(member, token.getAccessToken()));
        } else {
            return ApiResponse.onFailure(ErrorStatus._MEMBER_NOT_FOUND.getCode(), ErrorStatus._MEMBER_NOT_FOUND.getMessage(), null);
        }
    }


//    @PostMapping("/token/logout")
//    public ApiResponse<?> logout(@RequestHeader("Authorization") final String accessToken) {
//
//        // 엑세스 토큰으로 현재 Redis 정보 삭제
//        tokenService.removeRefreshToken(accessToken);
//        System.out.println("accessToken = " + accessToken + "로그아웃 됨");
//        return ApiResponse.onSuccess(SuccessStatus._OK);
//    }
    @PostMapping("/token/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String accessToken) {
        try {
            if (accessToken != null && accessToken.startsWith("Bearer ")) {
                accessToken = accessToken.substring(7);
            }

            // 엑세스 토큰으로 현재 Redis 정보 삭제
            boolean isRemoved = tokenService.removeRefreshToken(accessToken);
            if (isRemoved) {
                System.out.println("accessToken = " + accessToken + " 로그아웃 성공");
                return ResponseEntity.ok(ApiResponse.onSuccess(SuccessStatus._OK));
            } else {
                System.out.println("로그아웃 실패: 토큰이 존재하지 않거나 이미 만료됨.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.onFailure(ErrorStatus._TOKEN_UNSUPPORTED.getCode(), "토큰이 존재하지 않거나 이미 만료되었습니다.", null));
            }
        } catch (Exception e) {
            System.out.println("로그아웃 중 서버 에러 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR.getCode(), "서버 에러 발생", null));
        }
    }

    @PostMapping("/token/refresh")
    public ApiResponse<GeneratedToken> refresh(@RequestHeader("Authorization") final String accessToken) {

        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        Optional<RefreshToken> refreshToken = tokenRepository.findByAccessToken(accessToken);

        // RefreshToken이 존재하고 유효하다면 실행
        if (refreshToken.isPresent() && jwtUtil.verifyToken(refreshToken.get().getRefreshToken())) {
            // RefreshToken 객체를 꺼내온다.
            RefreshToken resultToken = refreshToken.get();
            // 권한과 아이디를 추출해 새로운 액세스토큰을 만든다.
            String newAccessToken = jwtUtil.generateAccessToken(resultToken.getUsername(), jwtUtil.getRole(resultToken.getRefreshToken()));
            // 액세스 토큰의 값을 수정해준다.
            resultToken.updateAccessToken(newAccessToken);
            tokenRepository.save(resultToken);
            // 새로운 액세스 토큰을 반환해준다.
            return ApiResponse.onSuccess(JwtConverter.toRefreshDto(newAccessToken, resultToken.getUsername()));
        }

        return ApiResponse.onFailure(ErrorStatus._TOKEN_EXPIRED.getCode(), ErrorStatus._TOKEN_EXPIRED.getMessage(), null);
    }

}