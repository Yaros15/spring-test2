package com.example.springtest2.controller;

import com.example.springtest2.model.Order;
import com.example.springtest2.model.Product;
import com.example.springtest2.repository.OrderRepository;
import com.example.springtest2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductRestController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    @Autowired
    public ProductRestController(ProductRepository productRepository,
                                 OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public Iterable<Product> all() {
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getOne(@PathVariable("id") Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        if(product.getNameProduct() == null){
            return ResponseEntity.badRequest().build();
        }
        if(product.getPrice() < 0){
            return ResponseEntity.badRequest().build();
        }
        Product newProduct = productRepository.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public Product update(@PathVariable("id") Long id,
                           @RequestBody Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @DeleteMapping ("{id}")
    public ResponseEntity<String> delete (@PathVariable ("id") Long id){
        Product product = productRepository.findById(id).orElse(null);
        if (product == null){
            return ResponseEntity.badRequest().body("Продукта с таким id нет");
        }
        List<Order> orders = orderRepository.getOrdersByProductsId(id);
        if(orders.size() > 0){
            return ResponseEntity.badRequest().body("Этот продукт есть в заказах");
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("Продукт удален");
    }
}
