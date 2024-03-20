package Fo.Suzip.web.controller;

import Fo.Suzip.domain.Member;
import Fo.Suzip.service.MemberService;
import Fo.Suzip.web.dto.JwtDto;
import Fo.Suzip.web.dto.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/local/signup")
    public ResponseEntity<Member> signUp(@RequestBody SignUpForm form){
        return ResponseEntity.ok(memberService.signUp(form));
    }

    @PostMapping("/local/signin")
    public ResponseEntity<JwtDto> signIn(@RequestBody SignUpForm form){
        return ResponseEntity.ok(memberService.signIn(form));
    }

}