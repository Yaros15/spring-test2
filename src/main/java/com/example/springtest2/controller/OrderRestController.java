package com.example.springtest2.controller;

import com.example.springtest2.dto.OrderDTO;
import com.example.springtest2.model.Customer;
import com.example.springtest2.model.Order;
import com.example.springtest2.model.Product;
import com.example.springtest2.repository.CustomerRepository;
import com.example.springtest2.repository.OrderRepository;
import com.example.springtest2.repository.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderRestController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    @Autowired
    public OrderRestController(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public Iterable<Order> all() {
        return orderRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> getOne (@PathVariable ("id") Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> create (@RequestBody OrderDTO orderDTO){
        Long customerId = orderDTO.getCustomerId();
        List<Long> productId = orderDTO.getProductIds();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        List<Product> products = productRepository.findAllById(productId);
        if (customer == null) {
            return ResponseEntity.badRequest().build();
        }
        if (products.size() == 0) {
            return ResponseEntity.badRequest().build();
        }
        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(products);
        Order newOrder = orderRepository.save(order);
        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public Order updateOrder (@PathVariable ("id") Long id,
                              @RequestBody OrderDTO orderDTO){
        //необходимо обновлять существующий в базе ордер и проверять что он существует
        Long customerId = orderDTO.getCustomerId();
        List<Long> productId = orderDTO.getProductIds();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        List<Product> products = productRepository.findAllById(productId);
        Order order = new Order();
        order.setId(id);
        order.setCustomer(customer);
        order.setProducts(products);
        return orderRepository.save(order);
    }

    @DeleteMapping("{id}")
    public void deleteOrder(@PathVariable ("id") Long id){
        orderRepository.deleteById(id);
    }
}
