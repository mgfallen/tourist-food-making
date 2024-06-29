package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RecipeList implements Serializable {
    private String mealtime;
    private String recipeName;

}
