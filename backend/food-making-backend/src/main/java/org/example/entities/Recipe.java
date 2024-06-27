package org.example.entities;

import jakarta.persistence.Convert;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.example.support.ProductsList;

@Entity
@Data
@Table(name = "recipe")
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
//@Convert(attributeName = "products", converter = StringJsonUserType.class)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private Long recipeId;



    @Column(nullable = false)
    private String name;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<ProductsList> products;

    @Column
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column
    private String mealtime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_recipe",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> orders = new HashSet<>();



    @Column
    private Integer persons;



}
