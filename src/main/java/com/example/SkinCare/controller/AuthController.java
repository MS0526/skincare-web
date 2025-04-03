package com.example.SkinCare.controller;

import com.example.SkinCare.model.User;
import com.example.SkinCare.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder; // 🔐 추가
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // 🔐 추가

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password,
            Model model) {

        User user = userService.getUserByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // 로그인 성공
            model.addAttribute("username", user.getUsername());
            return "redirect:/home";
        } else {
            // 로그인 실패
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }
}
