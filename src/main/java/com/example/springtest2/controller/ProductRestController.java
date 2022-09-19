package com.example.springtest2.controller;

import com.example.springtest2.model.Product;
import com.example.springtest2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductRestController {

    private final ProductRepository productRepository;
    @Autowired
    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public Iterable<Product> all() {
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    public Product getOne(@PathVariable("id") Product product) {
        return product;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("{id}")
    public Product update(@PathVariable("id") Long id,
                           @RequestBody Product product) {
        product.setId(id);
        return productRepository.save(product);
    }
}
