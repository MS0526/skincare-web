package com.example.SkinCare.controller;

import com.example.SkinCare.model.User;
import com.example.SkinCare.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder; // 🔐 추가
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // 🔐 비밀번호 암호화용

    // 생성자 주입
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 처리
    @PostMapping("/user/register")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        System.out.println("회원가입 컨트롤러 진입");

        if (bindingResult.hasErrors()) {
            System.out.println("유효성 검사 실패");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "register";
        }

        // 🔐 비밀번호 암호화 처리
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        user.setRole("USER");
        userService.saveUser(user);

        System.out.println("회원가입 저장 완료");

        model.addAttribute("message", "회원가입이 완료되었습니다.");
        return "register_success";
    }
}
