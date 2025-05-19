package com.example.SkinCare.service;

import com.example.SkinCare.model.Ingredient;
import com.example.SkinCare.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Optional<Ingredient> searchByName(String name) {
        return ingredientRepository.findByName(name);
    }

    public List<Ingredient> searchByNameLike(String keyword) {
        return ingredientRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Ingredient> findByNames(List<String> names) {
        System.out.println("🔍 검색할 성분 키워드: " + names);
        List<Ingredient> found = ingredientRepository.findByNameIn(names);
        System.out.println("✅ 매칭된 성분 개수: " + found.size());
        return found;
    }

    public List<Ingredient> findByKeywordPartialMatch(List<String> keywords) {
        List<Ingredient> all = ingredientRepository.findAll();
        return all.stream()
                .filter(ing -> keywords.stream().anyMatch(k -> ing.getName().contains(k) || k.contains(ing.getName()) // 양방향
                                                                                                                      // 부분
                                                                                                                      // 포함
                ))
                .distinct()
                .toList();
    }

}
