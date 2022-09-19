package com.example.springtest2.repository;

import com.example.springtest2.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository <Customer, Long> {
}
