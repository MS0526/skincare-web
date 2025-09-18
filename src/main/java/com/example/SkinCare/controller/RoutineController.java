package com.example.SkinCare.controller;

import com.example.SkinCare.model.Routine;
import com.example.SkinCare.model.User;
import com.example.SkinCare.service.RoutineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// 이 클래스가 Spring MVC의 컨트롤러 역할을 함을 나타냅니다.
@Controller
// 이 컨트롤러의 모든 메소드는 "/routine"이라는 기본 URL 경로를 가집니다.
@RequestMapping("/routine")
public class RoutineController {

    // 루틴 관련 비즈니스 로직을 처리하는 서비스 객체입니다. (final로 선언하여 불변성 보장)
    private final RoutineService routineService;

    // 생성자를 통해 RoutineService의 의존성을 주입(DI)받습니다.
    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    // HTTP GET 방식으로 "/routine" 경로의 요청을 처리합니다.
    @GetMapping("")
    public String viewRoutine(Model model, HttpSession session) {
        // 현재 세션에서 "loginUser"라는 이름으로 저장된 사용자 정보를 가져옵니다.
        User user = (User) session.getAttribute("loginUser");
        // 만약 사용자 정보가 없다면 (로그인하지 않았다면)
        if (user == null) {
            // 로그인 페이지로 리다이렉트(강제 이동)시킵니다.
            return "redirect:/login";
        }

        // 로그인한 사용자의 모든 루틴을 서비스 계층을 통해 조회합니다.
        var routines = routineService.getRoutinesByUser(user);
        // 조회된 루틴 목록을 "routines"라는 이름으로 모델에 추가하여, 뷰(HTML)에서 사용할 수 있도록 합니다.
        model.addAttribute("routines", routines);
        // "routine_list"라는 이름의 HTML 템플릿을 사용자에게 보여줍니다.
        return "routine_list";
    }

    // HTTP POST 방식으로 "/routine/add" 경로의 요청을 처리합니다. (새로운 루틴 추가)
    @PostMapping("/add")
    public String addRoutine(@RequestParam String timeOfDay, // 폼에서 전송된 'timeOfDay' 파라미터를 받습니다.
            @RequestParam String content, // 폼에서 전송된 'content' 파라미터를 받습니다.
            HttpSession session) {
        // 현재 로그인한 사용자 정보를 가져옵니다.
        User user = (User) session.getAttribute("loginUser");
        // 로그인하지 않았다면 로그인 페이지로 보냅니다.
        if (user == null)
            return "redirect:/login";

        // 새로운 Routine 객체를 생성합니다.
        Routine routine = new Routine();
        // 파라미터로 받은 값들을 Routine 객체의 필드에 설정합니다.
        routine.setTimeOfDay(timeOfDay);
        routine.setContent(content);
        // 루틴의 소유자로 현재 로그인한 사용자를 설정합니다.
        routine.setUser(user);

        // 완성된 Routine 객체를 서비스를 통해 데이터베이스에 저장합니다.
        routineService.saveRoutine(routine);
        // 루틴 목록 페이지로 리다이렉트하여 추가된 결과를 바로 볼 수 있게 합니다.
        return "redirect:/routine";
    }

    // HTTP POST 방식으로 "/routine/delete/{id}" 경로의 요청을 처리합니다. (예: /routine/delete/5)
    @PostMapping("/delete/{id}")
    public String deleteRoutine(@PathVariable Long id) { // URL 경로에 포함된 id 값을 파라미터로 받습니다.
        // 서비스 계층에 요청하여 해당 id를 가진 루틴을 데이터베이스에서 삭제합니다.
        routineService.deleteRoutine(id);
        // 루틴 목록 페이지로 리다이렉트합니다.
        return "redirect:/routine";
    }
}
