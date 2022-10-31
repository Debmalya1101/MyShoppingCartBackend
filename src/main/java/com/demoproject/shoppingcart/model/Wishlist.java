package com.demoproject.shoppingcart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wishlist") 
public class Wishlist {
	@Id
	private Long productId;
	private String name;
    private Long price;
	public Wishlist() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Wishlist(Long id, Long productId, String name, Long price) {
		super();
		//this.id = id;
		this.productId = productId;
		this.name = name;
		this.price = price;
	}
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
}
