package org.example.controllers;

import org.example.entities.Product;
import org.example.services.ProductDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductDataService productDataService;

    public ProductController(ProductDataService productDataService) {
        this.productDataService = productDataService;
    }



    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productDataService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<Product> getProductById(@PathVariable("product_id") Long productId) {
        Optional<Product> product = productDataService.getProductById(productId);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
