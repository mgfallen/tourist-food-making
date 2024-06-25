package org.example.controllers;

import org.example.entities.Product;
import org.example.services.ProductDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private final ProductDataService productDataService;

    public ProductController(ProductDataService productDataService) {
        this.productDataService = productDataService;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> productList = productDataService.getAllProducts();
        return ResponseEntity.ok(productList);
    }
}
