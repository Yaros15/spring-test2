package com.example.springtest2.model;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_customer")
    private String nameCustomer;
    private int age;

    public Customer(){  }

    public Customer (String nameCustomer, int age){
        this.nameCustomer = nameCustomer;
        this.age = age;
    }

    public long getId (){
        return id;
    }

    public void setId (long id){
        this.id = id;
    }

    public String getNameCustomer(){
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer){
        this.nameCustomer = nameCustomer;
    }

    public int getAge (){
        return age;
    }

    public void setAge (int age){
        this.age = age;
    }

}
