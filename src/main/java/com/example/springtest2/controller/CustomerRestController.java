package com.example.springtest2.controller;

import com.example.springtest2.model.Customer;
import com.example.springtest2.model.Order;
import com.example.springtest2.repository.CustomerRepository;
import com.example.springtest2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerRestController {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    @Autowired
    public CustomerRestController(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public Iterable<Customer> all() {
        return customerRepository.findAll();
    }

    @GetMapping("{id}")
    public Customer getOne(@PathVariable("id") Customer customer) {
        return customer;
    }

    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PutMapping("{id}")
    public Customer update(@PathVariable("id") Long id,
                           @RequestBody Customer customer) {
        customer.setId(id);
        return customerRepository.save(customer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Customer> delete(@PathVariable("id") Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        //научиться пояснять в ответе причину по которой запрос плохой
        if (customer == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Order> orders = orderRepository.getOrdersByCustomerId(id);
        if (orders.size() > 0) {
            return ResponseEntity.badRequest().build();
        }
        customerRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
