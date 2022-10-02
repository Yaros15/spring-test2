package com.example.springtest2.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table (name = "orders")
public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "customer_id")
    private Customer customer;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "order_")
    private List<Product> products = new LinkedList<>();

    public Order (){ }

    public Order (Customer customer, List<Product> products){
        this.customer = customer;
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
