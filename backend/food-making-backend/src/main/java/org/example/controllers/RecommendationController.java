package org.example.controllers;

import org.apache.coyote.Response;
import org.example.entities.Order;
import org.example.entities.Product;
import org.example.entities.Recipe;
import org.example.services.OrderDataService;
import org.example.services.RecipeDataService;
import org.example.services.RecommendationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v2/recommendation")
public class RecommendationController {
    @Autowired
    private RecommendationDataService recommendationDataService;
    private OrderDataService orderDataService;

    @PostMapping
    public ResponseEntity<?> getRecommendation(@RequestBody Map<String, String> parameters){
        Order order = new Order();
        Integer persons = Integer.valueOf(parameters.get("num_people"));
        Integer days = Integer.valueOf(parameters.get("num_days"));
        List<Recipe> allowedRecipes = recommendationDataService.getRecipesWithExclude(Arrays.asList(parameters.get("excluded_food").split(",")));
        List<Recipe> breakfastRecipesStream = allowedRecipes.stream()
                .filter(recipe -> "breakfast".equalsIgnoreCase(recipe.getMealtime()))
                .toList();

        List<Recipe> lunchRecipesStream = allowedRecipes.stream()
                .filter(recipe -> "lunch".equalsIgnoreCase(recipe.getMealtime()))
                .toList();

        List<Recipe> dinnerRecipesStream = allowedRecipes.stream()
                .filter(recipe -> "dinner".equalsIgnoreCase(recipe.getMealtime()))
                .toList();

        return ResponseEntity.ok(allowedRecipes);
    }

}
