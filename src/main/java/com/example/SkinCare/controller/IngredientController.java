package com.example.SkinCare.controller;

import com.example.SkinCare.service.IngredientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        var result = ingredientService.searchByName(name);
        if (result.isPresent()) {
            model.addAttribute("ingredient", result.get());
        } else {
            model.addAttribute("error", "해당 성분을 찾을 수 없습니다.");
        }
        return "ingredient_result";
    }
}
