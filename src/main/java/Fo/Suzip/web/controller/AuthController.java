package Fo.Suzip.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
//
//    private final RefreshTokenRepository tokenRepository;
//    private final RefreshTokenService tokenService;
//    private final JwtUtil jwtUtil;
//    private final EmitterRepository emitterRepository;
//


    // 카카오 회원가입
    @PostMapping("/login/oauth2/code/kakao")
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드

        System.out.println("KaKao code = " + code);
//        SignupSocialDto signupKakaoDto = kakaoUserService.kakaoLogin(code);
//        response.addHeader(AUTH_HEADER, signupKakaoDto.getToken());
//
//        return signupKakaoDto.getUserId();
    }
    @PostMapping("/login/oauth2/code/naver")
    public void naverLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드

        System.out.println("naver code = " + code);
//        SignupSocialDto signupKakaoDto = kakaoUserService.kakaoLogin(code);
//        response.addHeader(AUTH_HEADER, signupKakaoDto.getToken());
//
//        return signupKakaoDto.getUserId();
    }
    @PostMapping("/login/oauth2/code/google")
    public void googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드

        System.out.println("google code = " + code);
//        SignupSocialDto signupKakaoDto = kakaoUserService.kakaoLogin(code);
//        response.addHeader(AUTH_HEADER, signupKakaoDto.getToken());
//
//        return signupKakaoDto.getUserId();
    }
//    @PostMapping("/signup")
//    public ApiResponse<MemberResponseDTO.JoinResultDto> join(@RequestBody @Valid MemberRequestDTO.JoinDto request)  {
//
//        Member response = memberService.signup(request, url);
//        log.info(request.getName(), file);
//
//        return ApiResponse.onSuccess(MemberConverter.toJoinResult(response, jwtProvider));
//    }

//    @PostMapping("token/logout")
//    public ResponseEntity<StatusResponseDto> logout(@RequestHeader("Authorization") final String accessToken) {
//
//        // 엑세스 토큰으로 현재 Redis 정보 삭제
//        tokenService.removeRefreshToken(accessToken);
//        return ResponseEntity.ok(StatusResponseDto.addStatus(200));
//    }
//
//    @PostMapping("/token/refresh")
//    public ResponseEntity<TokenResponseStatus> refresh(@RequestHeader("Authorization") final String accessToken) {
//
//        // 액세스 토큰으로 Refresh 토큰 객체를 조회
//        Optional<RefreshToken> refreshToken = tokenRepository.findByAccessToken(accessToken);
//
//        // RefreshToken이 존재하고 유효하다면 실행
//        if (refreshToken.isPresent() && jwtUtil.verifyToken(refreshToken.get().getRefreshToken())) {
//            // RefreshToken 객체를 꺼내온다.
//            RefreshToken resultToken = refreshToken.get();
//            // 권한과 아이디를 추출해 새로운 액세스토큰을 만든다.
//            String newAccessToken = jwtUtil.generateAccessToken(resultToken.getId(), jwtUtil.getRole(resultToken.getRefreshToken()));
//            // 액세스 토큰의 값을 수정해준다.
//            resultToken.updateAccessToken(newAccessToken);
//            tokenRepository.save(resultToken);
//            // 새로운 액세스 토큰을 반환해준다.
//            return ResponseEntity.ok(TokenResponseStatus.addStatus(200, newAccessToken));
//        }
//
//        return ResponseEntity.badRequest().body(TokenResponseStatus.addStatus(400, null));
//    }

}