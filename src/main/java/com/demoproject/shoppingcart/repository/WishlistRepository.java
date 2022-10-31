package com.demoproject.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demoproject.shoppingcart.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
	
	//public void deleteByProductId(Long productId);

}
