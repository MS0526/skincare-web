package com.example.SkinCare.controller;

import com.example.SkinCare.model.User;
import com.example.SkinCare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입 처리
    @PostMapping("/user/register")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시, 다시 회원가입 폼으로 돌아감
            return "register"; // 'register.html'로 다시 돌아갑니다.
        }
        System.out.println("Registering user: " + user.getUsername()); // 로그 추가
        userService.saveUser(user); // 사용자 정보 저장
        model.addAttribute("message", "회원가입이 완료되었습니다.");
        return "register_success"; // 회원가입 완료 페이지로 이동
    }
}
