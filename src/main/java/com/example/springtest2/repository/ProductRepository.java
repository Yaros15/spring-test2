package com.example.springtest2.repository;

import com.example.springtest2.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository <Product, Long> {
}
