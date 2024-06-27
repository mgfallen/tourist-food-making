package org.example.services;

import org.example.entities.Recipe;
import org.example.repositories.ProductRepository;
import org.example.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class RecipeDataService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProductRepository productRepository;



    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId);
    }


    public Optional<String> getRecipeDescription(Long recipeId) {
        return recipeRepository.findById(recipeId).map(Recipe::getDescription);
    }

    @Transactional
    public Optional<Recipe> updateRecipeDescription(Long recipeId, String description) {
        return recipeRepository.findById(recipeId).map(recipe -> {
            recipe.setDescription(description);
            return recipeRepository.save(recipe);
        });
    }


}