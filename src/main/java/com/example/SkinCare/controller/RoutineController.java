package com.example.SkinCare.controller;

import com.example.SkinCare.model.Routine;
import com.example.SkinCare.model.User;
import com.example.SkinCare.service.RoutineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/routine")
public class RoutineController {

    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping("")
    public String viewRoutine(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/login";
        }

        var routines = routineService.getRoutinesByUser(user);
        model.addAttribute("routines", routines);
        return "routine_list";
    }

    @PostMapping("/add")
    public String addRoutine(@RequestParam String timeOfDay,
            @RequestParam String content,
            HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null)
            return "redirect:/login";

        Routine routine = new Routine();
        routine.setTimeOfDay(timeOfDay);
        routine.setContent(content);
        routine.setUser(user);

        routineService.saveRoutine(routine);
        return "redirect:/routine";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoutine(@PathVariable Long id) {
        routineService.deleteRoutine(id);
        return "redirect:/routine";
    }
}
