package com.demoproject.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoproject.shoppingcart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
