package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private Long recipeId;
    @ManyToMany(mappedBy = "recipes", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();
    private String name;
    private List<Product> productList;
    private String cooking;
}
