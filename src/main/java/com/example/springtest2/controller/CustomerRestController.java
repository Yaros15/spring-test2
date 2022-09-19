package com.example.springtest2.controller;

import com.example.springtest2.model.Customer;
import com.example.springtest2.repository.CustomerRepository;
import com.example.springtest2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
