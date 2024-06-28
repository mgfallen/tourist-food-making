package org.example.services;

import org.example.entities.Recipe;
import org.example.repositories.OrderRepository;
import org.example.repositories.ProductRepository;
import org.example.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecommendationDataService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public List<Recipe> getRecipesWithExclude(List<String> names) {return recipeRepository.getRecipesWithExcludedProducts(names);}




}
