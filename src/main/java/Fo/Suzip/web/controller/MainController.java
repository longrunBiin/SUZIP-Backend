package Fo.Suzip.web.controller;
import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@Slf4j
@RestController
@RequiredArgsConstructor
public class MainController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @GetMapping("/")
    @ResponseBody
    @Operation(summary = "메인화면 조회",description = "메인 화면을 조회 합니다")
    public String mainAPI() {

        return "main";
    }

    @GetMapping("/home")
    @ResponseBody
    @Operation(summary = "홈 화면 조회",description = "홈화면을 조회합니다")
    public String myAPI() {

        return "home";
    }

    @GetMapping("/health")
    @Operation(summary = "health check API",description = "aws loadBalancer health check를 위한 api")
    public String healthCHeck() {
        return "i'm healthy";
    }


//    @PostMapping(value = "/signup")
//    public ApiResponse<GeneratedToken> join(@RequestPart(name = "request") @Valid MemberRequestDTO.JoinDto request)  {
//
//        String url = null;
//
//        Member response = memberService.signup(request);
//        log.info(request.getEmail());
//
//        return ApiResponse.onSuccess(MemberConverter.toJoinResult(response, jwtUtil));
//    }
}