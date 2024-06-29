package org.example.services;

import org.example.entities.Recipe;
import org.example.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecommendationDataService {
    @Autowired
    private RecipeRepository recipeRepository;



    @Transactional
    public List<Recipe> getRecipesWithExclude(List<Integer> names) {return recipeRepository.getRecipesWithExcludedProducts(names);}




}
