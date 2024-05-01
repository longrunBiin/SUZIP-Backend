package Fo.Suzip.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OAuth2RedirectController {
    @GetMapping("/login")
    public String login() {
//        return "redirect:/oauth2/authorization/" + provider;
        return "login";
    }


}
