package com.example.SkinCare.controller;

import com.example.SkinCare.model.User;
import com.example.SkinCare.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // --- 기존 공개 메소드들 (내용만 간결하게 변경) ---

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (isNotLoggedIn(loginUser)) { // 헬퍼 메소드로 체크
            return "redirect:/login";
        }
        model.addAttribute("username", loginUser.getUsername());
        return "home";
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (isNotLoggedIn(loginUser)) { // 헬퍼 메소드로 체크
            return "redirect:/login";
        }
        if (isNotUser(loginUser)) { // 헬퍼 메소드로 체크
            return "redirect:/home";
        }
        model.addAttribute("username", loginUser.getUsername());
        return "mypage";
    }

    @GetMapping("/admin")
    public String adminPage(HttpSession session, Model model, HttpServletResponse response) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (isNotLoggedIn(loginUser)) { // 헬퍼 메소드로 체크
            return "redirect:/login";
        }
        if (isNotAdmin(loginUser)) { // 헬퍼 메소드로 체크
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "error/403";
        }
        model.addAttribute("username", loginUser.getUsername());
        return "admin";
    }

    @GetMapping("/admin/users")
    public String userList(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (isNotLoggedIn(loginUser) || isNotAdmin(loginUser)) { // 체크 로직을 한 줄로 결합
            return "error/403"; // 혹은 로그인 페이지로 보낼 수 있습니다.
        }
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("loginUserId", loginUser.getId());
        return "admin_user_list";
    }

    @PostMapping("/doLogin")
    public String login(@RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {
        User user = userService.getUserByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("loginUser", user);
            return "redirect:" + ("ADMIN".equals(user.getRole()) ? "/admin" : "/home");
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (isNotLoggedIn(loginUser) || isNotAdmin(loginUser)) { // 체크 로직을 한 줄로 결합
            return "error/403";
        }
        if (!loginUser.getId().equals(id)) {
            userService.deleteUserById(id);
        }
        return "redirect:/admin/users";
    }

    // --- [새로 추가된 부분] 인증/인가 확인을 위한 private 헬퍼 메소드 ---

    /**
     * 사용자가 로그인하지 않은 상태인지 확인합니다.
     * 
     * @param user HttpSession에서 가져온 User 객체
     * @return 로그인하지 않았으면 true, 로그인했으면 false
     */
    private boolean isNotLoggedIn(User user) {
        return user == null;
    }

    /**
     * 사용자가 ADMIN 역할이 아닌지 확인합니다.
     * 
     * @param user 로그인한 User 객체
     * @return ADMIN이 아니면 true, ADMIN이면 false
     */
    private boolean isNotAdmin(User user) {
        return !"ADMIN".equals(user.getRole());
    }

    /**
     * 사용자가 USER 역할이 아닌지 확인합니다.
     * 
     * @param user 로그인한 User 객체
     * @return USER가 아니면 true, USER이면 false
     */
    private boolean isNotUser(User user) {
        return !"USER".equals(user.getRole());
    }
}
