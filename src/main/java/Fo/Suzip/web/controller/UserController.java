package Fo.Suzip.web.controller;

import Fo.Suzip.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/info")
    public ResponseEntity<Member> info(@AuthenticationPrincipal Member member){
        return ResponseEntity.ok(member);
    }
}