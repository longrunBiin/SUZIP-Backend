package Fo.Suzip.web.controller;
import Fo.Suzip.apiPayload.ApiResponse;
import Fo.Suzip.converter.MemberConverter;
import Fo.Suzip.domain.Member;
import Fo.Suzip.jwt.JwtUtil;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.web.dto.GeneratedToken;
import Fo.Suzip.web.dto.MemberRequestDTO;
import Fo.Suzip.web.dto.MemberResponseDTO;
import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
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