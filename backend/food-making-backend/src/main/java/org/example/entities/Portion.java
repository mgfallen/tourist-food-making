package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.example.DTO.ProductsList;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Data
@Table(name = "portions")
public class Portion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long portion_id;

    @OneToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


    @JdbcTypeCode(SqlTypes.JSON)
    private List<ProductsList> products;

    @Column(name = "summary_price")
    private Long summaryPrice;

}
