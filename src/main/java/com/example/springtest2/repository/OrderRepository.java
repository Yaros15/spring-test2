package com.example.springtest2.repository;

import com.example.springtest2.model.Customer;
import com.example.springtest2.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository <Order, Long> {
    void deleteByCustomer (Customer customer);
}
