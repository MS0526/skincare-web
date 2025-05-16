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

}
