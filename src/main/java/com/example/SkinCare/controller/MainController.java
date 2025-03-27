package com.example.SkinCare.controller;

import com.example.SkinCare.model.User;
import com.example.SkinCare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.getUserByUsername(username); // 사용자 정보 조회
            model.addAttribute("user", user); // 사용자 정보를 템플릿에 전달
        } else {
            model.addAttribute("username", "Guest");
        }
        model.addAttribute("title", "홈 페이지");
        return "index";
    }
}
