package com.example.SkinCare.service;

import com.example.SkinCare.model.Ingredient;
import com.example.SkinCare.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j; // SLF4J 로거 임포트
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Lombok을 사용하여 SLF4J 로거(log)를 자동으로 추가합니다.
@Slf4j
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // 성분 이름으로 정확히 일치하는 성분을 검색합니다.

    public Optional<Ingredient> searchByName(String name) {
        return ingredientRepository.findByName(name);
    }

    // 키워드가 포함된 모든 성분을 검색합니다. (대소문자 구분 없음)

    public List<Ingredient> searchByNameLike(String keyword) {
        return ingredientRepository.findByNameContainingIgnoreCase(keyword);
    }

    // 여러 개의 성분 이름과 정확히 일치하는 모든 성분을 한 번에 검색합니다.

    public List<Ingredient> findByNames(List<String> names) {
        log.debug("Searching for ingredients with exact names: {}", names); // 로깅 방식으로 변경
        List<Ingredient> found = ingredientRepository.findByNameIn(names);
        log.info("Found {} ingredients with exact names.", found.size()); // 로깅 방식으로 변경
        return found;
    }

    /**
     * OCR로 추출된 키워드 목록과 부분적으로 일치하는 모든 성분을 찾습니다.
     * 
     * @param keywords OCR로 추출된 성분 키워드 후보 목록
     * @return 부분적으로 일치하는 모든 Ingredient 객체의 중복 없는 리스트
     */
    public List<Ingredient> findByKeywordPartialMatch(List<String> keywords) {
        log.debug("Finding partial matches for keywords: {}", keywords);

        // [성능 주의] 이 방식은 DB의 모든 성분 데이터를 메모리로 가져와 애플리케이션에서 필터링합니다.
        // 성분 데이터가 수만 건 이상으로 많아질 경우, DB에서 직접 쿼리하도록 로직을 변경해야 합니다.
        List<Ingredient> allIngredients = ingredientRepository.findAll();

        List<Ingredient> matchedIngredients = allIngredients.stream()
                .filter(ingredient -> isIngredientMatch(ingredient, keywords)) // 헬퍼 메소드로 로직 분리
                .distinct()
                .toList();

        log.info("Found {} matching ingredients for {} keywords.", matchedIngredients.size(), keywords.size());
        return matchedIngredients;
    }

    /**
     * 특정 성분이 키워드 목록과 부분적으로 일치하는지 확인하는 헬퍼(helper) 메소드.
     * 
     * @param ingredient 검사할 성분 객체
     * @param keywords   비교할 키워드 목록
     * @return 하나라도 매칭되면 true, 아니면 false
     */
    private boolean isIngredientMatch(Ingredient ingredient, List<String> keywords) {
        final String ingredientName = ingredient.getName();
        return keywords.stream()
                .anyMatch(keyword -> ingredientName.contains(keyword) || keyword.contains(ingredientName));
    }
}
