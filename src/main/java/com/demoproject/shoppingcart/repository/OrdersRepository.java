package com.demoproject.shoppingcart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoproject.shoppingcart.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long>{
	public List<Orders> findByUserId(int userId);
}
