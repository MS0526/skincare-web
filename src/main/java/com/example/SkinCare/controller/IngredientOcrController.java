package com.example.SkinCare.controller;

import com.example.SkinCare.model.Ingredient;
import com.example.SkinCare.service.IngredientService;
import com.example.SkinCare.service.OcrService;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/ingredient")
@MultipartConfig
public class IngredientOcrController {

    private final OcrService ocrService;
    private final IngredientService ingredientService;

    public IngredientOcrController(OcrService ocrService, IngredientService ingredientService) {
        this.ocrService = ocrService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ocr")
    public String ocrUploadForm() {
        return "ingredient_ocr_upload"; // 업로드 폼 템플릿
    }

    // IngredientOcrController.java
    @PostMapping("/ocr")
    public String handleOcrUpload(@RequestParam("image") MultipartFile file, Model model) {
        try {
            String text = ocrService.extractTextFromImage(file);
            System.out.println("🔍 OCR 결과 텍스트:\n" + text);

            model.addAttribute("extractedText", text);

            List<String> stopwords = List.of("사용법", "주의사항", "제조", "성분", "기타", "정보", "설명서", "외부", "번호", "바코드");
            List<String> keywords = Arrays.stream(text.split("[,\\s\\n\\r·:\\-()]+"))
                    .map(s -> s.replaceAll("[^가-힣]", ""))
                    .filter(s -> s.length() >= 2 && !stopwords.contains(s))
                    .distinct()
                    .toList();

            System.out.println("🧾 정제된 성분 키워드: " + keywords);

            // 🔁 IngredientService를 통해 매칭
            List<Ingredient> matched = ingredientService.findByKeywordPartialMatch(keywords);

            model.addAttribute("matchedIngredients", matched);
        } catch (Exception e) {
            model.addAttribute("error", "OCR 분석 중 오류 발생: " + e.getMessage());
        }

        return "ingredient_ocr_result";
    }

}
