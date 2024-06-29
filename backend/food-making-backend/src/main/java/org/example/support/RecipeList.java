package org.example.support;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
public class RecipeList implements Serializable {
    private String mealtime;
    private String recipeName;

}
