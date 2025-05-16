package com.example.SkinCare.controller;

import com.example.SkinCare.model.Ingredient;
import com.example.SkinCare.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/search")
    public String searchForm() {
        return "ingredient_search";
    }

    @PostMapping("/search")
    public String searchResult(@RequestParam String name, Model model) {
        List<Ingredient> results = ingredientService.searchByNameLike(name);

        if (results.isEmpty()) {
            model.addAttribute("error", "해당 성분을 찾을 수 없습니다.");
        } else {
            model.addAttribute("results", results); // 여러 개 가능성
        }

        return "ingredient_results"; // 복수 결과 템플릿
    }
}
