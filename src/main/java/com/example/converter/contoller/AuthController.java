package com.example.converter.contoller;

import com.example.converter.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    @PostMapping("/registration")
    public String registration(@RequestParam("login") String login, @RequestParam("password") String password, Model model){
        if(!registrationService.registerUser(login, password)){
            model.addAttribute("error","такой логин уже занят");
            return "registration";
        }
        return "redirect:/login";
    }
}
