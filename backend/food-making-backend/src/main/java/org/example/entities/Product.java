package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product")
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
    private Integer carbohydrates;

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

    public Product(Long productId, String name, Integer proteins, Integer fats, Integer carbohydrates, String link, Integer kilocalories, Integer price, Integer packWeight, Boolean perishable, String category) {
        this.productId = productId;
        this.name = name;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.link = link;
        this.kilocalories = kilocalories;
        this.price = price;
        this.packWeight = packWeight;
        this.perishable = perishable;
        this.category = category;
    }

    public Product() {

    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProteins() {
        return proteins;
    }

    public void setProteins(Integer proteins) {
        this.proteins = proteins;
    }

    public Integer getFats() {
        return fats;
    }

    public void setFats(Integer fats) {
        this.fats = fats;
    }

    public Integer getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Integer carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getKilocalories() {
        return kilocalories;
    }

    public void setKilocalories(Integer kilocalories) {
        this.kilocalories = kilocalories;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPackWeight() {
        return packWeight;
    }

    public void setPackWeight(Integer packWeight) {
        this.packWeight = packWeight;
    }

    public Boolean getPerishable() {
        return perishable;
    }

    public void setPerishable(Boolean perishable) {
        this.perishable = perishable;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
