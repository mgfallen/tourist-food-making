package org.example.controllers;

import org.example.entities.Recipe;
import org.example.services.RecipeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeDataService recipeDataService;



    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeDataService.getAllRecipes());
    }

    @GetMapping("/{recipe_id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long recipe_id) {
        Optional<Recipe> recipe = recipeDataService.getRecipeById(recipe_id);
        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }





    @GetMapping("/{recipe_id}/description")
    public ResponseEntity<?> getRecipeDescription(@PathVariable Long recipe_id) {
        Optional<String> description = recipeDataService.getRecipeDescription(recipe_id);
        return description.map(desc -> ResponseEntity.ok().body(Map.of("description", desc)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{recipe_id}/description")
    public ResponseEntity<?> updateRecipeDescription(@PathVariable Long recipe_id,  @RequestBody Map<String, String> description) {
        Optional<Recipe> updatedRecipe = recipeDataService.updateRecipeDescription(recipe_id, description.get("description"));
        return updatedRecipe.map(recipe -> ResponseEntity.status(201).build())
                .orElseGet(() -> ResponseEntity.status(204).build());
    }






}