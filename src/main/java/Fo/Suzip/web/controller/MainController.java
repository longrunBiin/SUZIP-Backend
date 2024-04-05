package Fo.Suzip.web.controller;
import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.service.MemberService;
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
    public String mainAPI() {

        return "main";
    }

    @GetMapping("/home")
    @ResponseBody
    public String myAPI() {

        return "home";
    }

    @GetMapping("/health")
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