package org.example.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer proteins;

    @Column(nullable = false)
    private Integer fats;

    @Column(nullable = false)
    private Integer carbonates;

    @Column(length = 150)
    private String link;

    @Column(nullable = false)
    private Integer kilocalories;

    @Column(nullable = false)
    private Integer price;

    @Column(name="pack_weight")
    private Integer packWeight;

    @Column(name = "perishable")
    private Boolean perishable;

    @Column(length = 50)
    private String category;


}
