package org.example.controllers;

import org.apache.coyote.Response;
import org.example.DTO.*;
import org.example.entities.Order;
import org.example.entities.Product;
import org.example.entities.Recipe;
import org.example.services.OrderDataService;
import org.example.services.RecipeDataService;
import org.example.services.RecommendationDataService;
import org.example.support.ProductsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/recommendation")
public class RecommendationController {
    @Autowired
    private RecommendationDataService recommendationDataService;

    @Autowired
    private OrderDataService orderDataService;



    @PostMapping
    public ResponseEntity<?> getRecommendation(@RequestBody RecParameters parameters){
        Random random = new Random();
        Integer persons = parameters.getNumPeople();
        Integer days = parameters.getNumDays();
        String budget = parameters.getBudget();
        List<Recipe> allowedRecipes = recommendationDataService.getRecipesWithExclude(parameters.getExcludedFood());
        System.out.println(allowedRecipes);
        List<Recipe> breakfastRecipes = new ArrayList<>(allowedRecipes.stream()
                .filter(recipe -> "breakfast".equalsIgnoreCase(recipe.getMealtime()))
                .sorted(Comparator.comparingInt(Recipe::getPrice))
                .toList());

        List<Recipe> lunchRecipes = new ArrayList<>(allowedRecipes.stream()
                .filter(recipe -> "lunch".equalsIgnoreCase(recipe.getMealtime()))
                .sorted(Comparator.comparingInt(Recipe::getPrice))
                .toList());

        List<Recipe> dinnerRecipes = new ArrayList<>(allowedRecipes.stream()
                .filter(recipe -> "dinner".equalsIgnoreCase(recipe.getMealtime()))
                .sorted(Comparator.comparingInt(Recipe::getPrice))
                .toList());
        System.out.println(breakfastRecipes.size());
        switch (budget){
            case "малый": {
               breakfastRecipes.subList(0, (int) (breakfastRecipes.size() * 0.4));
               lunchRecipes.subList(0, (int) (lunchRecipes.size() * 0.4));
               dinnerRecipes.subList(0, (int) (dinnerRecipes.size() * 0.4));
                break;}
            case "средний": {
                breakfastRecipes.subList((int) (breakfastRecipes.size() * 0.4), (int) (breakfastRecipes.size() * 0.7));
                lunchRecipes.subList((int) (lunchRecipes.size() * 0.4), (int) (lunchRecipes.size() * 0.7));
                dinnerRecipes.subList((int) (dinnerRecipes.size() * 0.4), (int) (dinnerRecipes.size() * 0.7));
                break;
            }
            case "большой": {
                breakfastRecipes.subList((int) (breakfastRecipes.size() * 0.8), breakfastRecipes.size());
                lunchRecipes.subList((int) (lunchRecipes.size() * 0.8), lunchRecipes.size());
                dinnerRecipes.subList((int) (dinnerRecipes.size() * 0.8), dinnerRecipes.size());
                break;
            }
        }
        System.out.println(breakfastRecipes.size());
        List<DayRecommendationDTO> recs = new ArrayList<>();
        HashMap<Long, Integer> repetitions = new HashMap<>();
        List<Recipe> breakfastschedule = new ArrayList<>(days);
        List<Recipe> lunchshedule = new ArrayList<>(days);
        List<Recipe> dinnershedule = new ArrayList<>(days);


        Collections.shuffle(breakfastRecipes, random);
        Collections.shuffle(lunchRecipes, random);
        Collections.shuffle(dinnerRecipes, random);

        for (int i = 0; i < days; i++) {
            if (i >= 3) {
                Recipe breakfastrecipeToAddBack = breakfastschedule.get(i - 3);
                if (!breakfastRecipes.contains(breakfastrecipeToAddBack)) {
                    breakfastRecipes.add(breakfastrecipeToAddBack);
                }
                Recipe lunchrecipeToAddBack = lunchshedule.get(i - 3);
                if (!lunchRecipes.contains(lunchrecipeToAddBack)) {
                    lunchRecipes.add(lunchrecipeToAddBack);
                }
                Recipe dinnerrecipeToAddBack = dinnershedule.get(i - 3);
                if (!dinnerRecipes.contains(dinnerrecipeToAddBack)) {
                    dinnerRecipes.add(dinnerrecipeToAddBack);
                }
            }


            Recipe selectedBreakfastRecipe = breakfastRecipes.remove(random.nextInt(breakfastRecipes.size()));
            breakfastschedule.add(selectedBreakfastRecipe);
            Recipe selectedLunchRecipe = lunchRecipes.remove(random.nextInt(lunchRecipes.size()));
            lunchshedule.add(selectedLunchRecipe);
            Recipe selectedDinnerRecipe = dinnerRecipes.remove(random.nextInt(dinnerRecipes.size()));
            dinnershedule.add(selectedDinnerRecipe);
            Collections.shuffle(breakfastRecipes, random);
            Collections.shuffle(lunchRecipes, random);
            Collections.shuffle(dinnerRecipes, random);
        }

        for (int i = 0; i < days; i++){
            RecommendationDTO dayrec = new RecommendationDTO();
            RecipeDTO breakfast = new RecipeDTO();
            RecipeDTO lunch = new RecipeDTO();
            RecipeDTO dinner = new RecipeDTO();
            breakfast.setRecipeId(breakfastschedule.get(i).getRecipeId());
            breakfast.setName(breakfastschedule.get(i).getName());
            lunch.setRecipeId(lunchshedule.get(i).getRecipeId());
            lunch.setName(lunchshedule.get(i).getName());
            dinner.setRecipeId(dinnershedule.get(i).getRecipeId());
            dinner.setName(dinnershedule.get(i).getName());
            dayrec.setBreakfast(breakfast);
            dayrec.setLunch(lunch);
            dayrec.setDinner(dinner);
            DayRecommendationDTO dayRecommendationDTO = new DayRecommendationDTO();
            dayRecommendationDTO.setDay(i + 1);
            dayRecommendationDTO.setRecipes(dayrec);
            recs.add(dayRecommendationDTO);
        }

        List<Recipe> allSchedule = new ArrayList<>();
        allSchedule.addAll(breakfastschedule);
        allSchedule.addAll(lunchshedule);
        allSchedule.addAll(dinnershedule);

        Map<Long, ProductsList> productMap = new HashMap<>();
        List<ProductsList> allProducts = new ArrayList<>();
        for (Recipe recipe : allSchedule) {
            for (ProductsList product : recipe.getProducts()) {
                int ind = allProducts.indexOf(product);
                if (ind > -1) {
                    allProducts.get(ind).setAmount(allProducts.get(ind).getAmount() + product.getAmount());
                } else {
                    ProductsList productsList = new ProductsList();
                    productsList.setProductId(product.getProductId());
                    productsList.setAmount(product.getAmount());
                    allProducts.add(productsList);
                }
            }
        }

        /*for (Recipe recipe : allSchedule) {
            for (ProductsList product : recipe.getProducts()) {
                productMap.merge(product.getProductId(), product, (existingProduct, newProduct) -> {
                    existingProduct.setAmount(existingProduct.getAmount() + newProduct.getAmount());
                    return existingProduct;
                });
            }
        }

        List<ProductsList> allProducts =  productMap.values().stream().toList();*/
        for (ProductsList productsList:
             allProducts) {
            productsList.setAmount(productsList.getAmount()*days*persons);
        }
        Order order = new Order();
        order.setProducts(allProducts);
        order.setRecipes(recs);
        orderDataService.createOrder(order);

        Long orderID = order.getOrderId(); // Assuming Order has a method to get the order ID

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderID(orderID);
        response.setRecommendations(recs);

        return new ResponseEntity<>(response, HttpStatus.OK);



    }

}
