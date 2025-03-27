package com.example.SkinCare.controller;

import com.example.SkinCare.model.User;
import com.example.SkinCare.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // 로그인 페이지로 이동
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register"; // 회원가입 페이지로 이동
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // 로그인 처리 후 인덱스로 리다이렉트
        model.addAttribute("username", username);
        return "redirect:/"; // 로그인 후 인덱스 페이지로 리다이렉트
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        userService.saveUser(user); // 사용자 저장
        model.addAttribute("message", "회원가입이 완료되었습니다. 로그인하세요.");
        return "register_success"; // 회원가입 성공 페이지로 이동
    }
}
