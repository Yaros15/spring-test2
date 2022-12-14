package com.example.springtest2.repository;

import com.example.springtest2.model.Customer;
import com.example.springtest2.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository <Order, Long> {
    List<Order> getOrdersByCustomerId(Long id);
    List<Order> getOrdersByProductsId(Long id);
}
