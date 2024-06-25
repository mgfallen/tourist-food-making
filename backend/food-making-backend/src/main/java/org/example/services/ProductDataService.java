package org.example.services;

import jakarta.transaction.Transactional;
import org.example.entities.Product;
import org.example.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDataService {

    private ProductRepository productRepository;

    @Transactional
    public Product getProductById(Long id) {
        return productRepository.getById(id);
    }

    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
