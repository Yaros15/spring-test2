package com.example.springtest2.controller;

import com.example.springtest2.model.Customer;
import com.example.springtest2.model.Order;
import com.example.springtest2.repository.CustomerRepository;
import com.example.springtest2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Customer> getOne(@PathVariable("id") Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        if (customer.getNameCustomer() == null){
            return ResponseEntity.badRequest().build();
        }
        if (customer.getAge() < 0){
            return ResponseEntity.badRequest().build();
        }
        Customer newCustomer = customerRepository.save(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
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
