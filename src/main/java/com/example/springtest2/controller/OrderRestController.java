package com.example.springtest2.controller;

import com.example.springtest2.dto.OrderDTO;
import com.example.springtest2.model.Customer;
import com.example.springtest2.model.Order;
import com.example.springtest2.model.Product;
import com.example.springtest2.repository.CustomerRepository;
import com.example.springtest2.repository.OrderRepository;
import com.example.springtest2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Order getOne (@PathVariable ("id") Order order){
        return order;
    }

    @PostMapping
    public Order create (@RequestBody OrderDTO orderDTO){
        Long customerId = orderDTO.getCustomerId();
        Long productId = orderDTO.getProductId();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        Order order = new Order();
        order.setCustomer(customer);
        order.setProduct(product);
        return orderRepository.save(order);
    }

    @PutMapping("{id}")
    public Order updateOrder (@PathVariable ("id") Long id,
                              @RequestBody OrderDTO orderDTO){
        Long customerId = orderDTO.getCustomerId();
        Long productId = orderDTO.getProductId();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        Order order = new Order();
        order.setId(id);
        order.setCustomer(customer);
        order.setProduct(product);
        return orderRepository.save(order);
    }

    @DeleteMapping("{id}")
    public void deleteOrder(@PathVariable ("id") Long id){
        orderRepository.deleteById(id);
    }

}
