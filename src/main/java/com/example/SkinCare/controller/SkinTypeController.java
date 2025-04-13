package com.example.SkinCare.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/skin")
public class SkinTypeController {

    // 설문 폼 페이지 GET 요청 처리
    @GetMapping("/diagnosis")
    public String surveyPage() {
        return "skin_survey"; // 설문 HTML 템플릿 보여줌
    }

    @GetMapping("/recommend")
    public String recommendPage(HttpSession session, Model model) {
        String skinType = (String) session.getAttribute("skinType");

        model.addAttribute("skinType", skinType);

        if (skinType == null) {
            return "skin_recommend";
        }

        List<String> recommendList = new ArrayList<>();

        switch (skinType) {
            case "건성":
                recommendList = List.of("히알루론산 보습 크림", "세라마이드 앰플", "촉촉한 수분 에센스");
                break;
            case "지성":
                recommendList = List.of("피지 조절 토너", "무기자차 선크림", "산뜻한 젤 크림");
                break;
            case "민감성":
                recommendList = List.of("센텔라 수딩 젤", "알로에 진정 패드", "무향 약산성 클렌저");
                break;
            case "복합성":
                recommendList = List.of("T존용 피지 조절 크림", "U존용 수분크림", "멀티 밸런스 세럼");
                break;
            default:
                recommendList = List.of("기본 보습제", "자극 없는 클렌저");
        }

        model.addAttribute("recommendList", recommendList);
        return "skin_recommend";
    }

    // 설문 제출 후 결과 분석 POST 처리
    @PostMapping("/diagnosis")
    public String diagnose(
            @RequestParam String q1,
            @RequestParam String q2,
            @RequestParam String q3,
            HttpSession session,
            Model model) {

        String result;

        // 간단한 분석 로직
        if (q1.equals("dry") && q2.equals("dry")) {
            result = "건성";
        } else if (q1.equals("oily") && q2.equals("oily")) {
            result = "지성";
        } else if (q3.equals("sensitive")) {
            result = "민감성";
        } else {
            result = "복합성";
        }

        session.setAttribute("skinType", result); // 세션에 저장
        model.addAttribute("result", result); // 결과 템플릿으로 전달

        return "skin_result";
    }
}
