package Fo.Suzip.web.controller;

import Fo.Suzip.domain.Member;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.service.OAuth2Service.CustomOAuth2UserService;
import Fo.Suzip.web.dto.GeneratedToken;
import Fo.Suzip.web.dto.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;
    private final CustomOAuth2UserService socialLoginService;

    @PostMapping("/local/signup")
    public ResponseEntity<Member> signUp(@RequestBody SignUpForm form){
        return ResponseEntity.ok(memberService.signUp(form));
    }

    @PostMapping("/local/signin")
    public ResponseEntity<GeneratedToken> signIn(@RequestBody SignUpForm form){
        return ResponseEntity.ok(memberService.signIn(form));
    }


//    @PostMapping("/social/{provider}")
//    public ResponseEntity<JwtDto> socialSignIn(@PathVariable String provider, String code) {
//        System.out.println("LoginController.socialSignIn");
//        SignUpForm signUpForm = socialLoginService.signIn(provider, code);
//        System.out.println("signUpForm = " + signUpForm);
//        return ResponseEntity.ok(memberService.socialSignIn(signUpForm));
//    }
//
//
//    @GetMapping("/oauth2/{provider}")
//    public void tryOAuth2(@PathVariable String provider, HttpServletResponse response)
//            throws IOException {
//        String url = socialLoginService.tryOAuth2(provider);
//        response.sendRedirect(url);
//    }
//    @GetMapping("/oauth2/code/{provider}")
//    public ResponseEntity<JwtDto> authorized(@PathVariable String provider, @RequestParam String code) {
//        return socialLoginService.connectToSocialSignIn(provider, code);
//    }
}