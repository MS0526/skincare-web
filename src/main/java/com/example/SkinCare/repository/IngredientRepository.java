package com.example.SkinCare.repository;

import com.example.SkinCare.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    // 정확히 일치하는 성분명 검색
    Optional<Ingredient> findByName(String name);

    // 부분 일치 검색 (대소문자 무시)
    List<Ingredient> findByNameContainingIgnoreCase(String keyword);

    // 여러 개 성분명 일치 (OCR 추출 후 사용)
    List<Ingredient> findByNameIn(List<String> names);
}
