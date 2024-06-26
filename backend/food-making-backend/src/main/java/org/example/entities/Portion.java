package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "portion")
public class Portion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long portion_id;

    @OneToOne(mappedBy = "recipe")
    private Long recipeId;

    @OneToMany (mappedBy = "product")
    private List<Product> products;

    @Column(name = "summary_price")
    private Long summaryPrice;

}
