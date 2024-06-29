package org.example.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.DTO.DayRecommendationDTO;
import org.example.DTO.RecipeDTO;
import org.example.DTO.RecommendationDTO;
import org.example.support.ProductsList;
import org.example.support.RecipeList;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;
    @Basic
    private LocalDate timestamp;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @JdbcTypeCode(SqlTypes.JSON)
    private List<DayRecommendationDTO> recipes;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<ProductsList> products;


    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDate.now();
    }

}
