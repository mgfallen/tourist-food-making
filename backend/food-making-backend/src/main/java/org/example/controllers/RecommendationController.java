package org.example.controllers;

import org.example.DTO.OrderResponseDTO;
import org.example.DTO.RecParameters;
import org.example.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/v1/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;


    @PostMapping
    public ResponseEntity<?> getRecommendation(@RequestBody RecParameters parameters){
        Integer persons = parameters.getNumPeople();
        Integer days = parameters.getNumDays();
        String budget = parameters.getBudget();
        List<Integer> excludedFood = parameters.getExcludedFood();
        List<String> cookware = parameters.getCookware();
        if (persons == null || days == null || budget == null || excludedFood == null || cookware == null) {
            return new ResponseEntity<>("Invalid input parameters", HttpStatus.BAD_REQUEST);
        }
        OrderResponseDTO response = recommendationService.getRecommendation(days, persons, budget, cookware, excludedFood);
        return new ResponseEntity<>(response, HttpStatus.OK);



    }

}
