package com.demoproject.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.demoproject.shoppingcart.model.Cart;
import com.demoproject.shoppingcart.model.Orders;
import com.demoproject.shoppingcart.model.Product;
import com.demoproject.shoppingcart.model.Wishlist;
import com.demoproject.shoppingcart.repository.CartRepository;
import com.demoproject.shoppingcart.repository.OrdersRepository;
import com.demoproject.shoppingcart.repository.ProductRepository;
import com.demoproject.shoppingcart.repository.WishlistRepository;

//Service Class is not created for this controller...
//Can be created in future

@RestController
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	ProductRepository repo;
	@Autowired
	CartRepository cartrepo;
	@Autowired
	WishlistRepository wishrepo;
	@Autowired
	OrdersRepository orderrepo;

	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	@Transactional
	@GetMapping("/products/{start}/{end}")
	public List<Product> getAllProductsByPrice(@PathVariable("start") Long start, @PathVariable("end") Long end) {
		return repo.findByPriceBetween(start, end);
	}


	// CartController for specific User
	@PostMapping("/cart/{userid}")
	public void addProductToCartByUser(@RequestBody Product product, @PathVariable("userid") int userId) {
		Cart cart = new Cart();
		System.out.println(cartrepo.findByProductIdAndUserId(product.getId(), userId));
		if (cartrepo.findByProductIdAndUserId(product.getId(), userId) == null) {
			cart.setQty(1L);
			cart.setProductId(product.getId());
			cart.setProductName(product.getName());
			cart.setPrice(product.getPrice());
			cart.setUserId(userId);
			cartrepo.save(cart);
		} else {
			cart = cartrepo.findByProductIdAndUserId(product.getId(), userId);
			cart.setQty(cart.getQty() + 1);
			cartrepo.save(cart);
		}
	}

	// CartController User specific

	@GetMapping("/cartitems/{userid}")
	public List<Cart> getCartItemsByUser(@PathVariable("userid") int userid) {
		return cartrepo.findByUserId(userid);
	}

	@Transactional
	@DeleteMapping("cart/{pid}/{userid}")
	public void removeFromCart(@PathVariable("pid") Long productId, @PathVariable("userid") int userId) {
		cartrepo.deleteByProductIdAndUserId(productId, userId);
	}

	@Transactional
	@DeleteMapping("cartall/{userid}")
	public void removeAllFromCart(@PathVariable("userid") int userId) {
		cartrepo.deleteByUserId(userId);
	}

	// WishlistController

	@PostMapping("/wishlist/{userid}")
	public void addToWishlistByUser(@RequestBody Product product, @PathVariable("userid") int userId) {
		Wishlist ob = new Wishlist();
		ob.setName(product.getName());
		ob.setProductId(product.getId());
		ob.setPrice(product.getPrice());
		ob.setUserId(userId);
		wishrepo.save(ob);
	}

	@GetMapping("/wishlist/{userid}")
	public List<Wishlist> getWishListByUser(@PathVariable("userid") int userId) {
		return wishrepo.findByUserId(userId);
	}

	@Transactional
	@DeleteMapping("/wishlist/{pid}/{userid}")
	public void removeFromWishlistByUser(@PathVariable("pid") Long productId, @PathVariable("userid") int userId) {
		wishrepo.deleteByProductIdAndUserId(productId, userId);
	}

	// Order History Controller

	@PostMapping("/myorders/{userid}")
	public void addToOrderHistory(@RequestBody Orders order, @PathVariable("userid") int userId) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy");

		LocalDateTime now = LocalDateTime.now();

		String dateTimeString = now.format(formatter);
		order.setUserId(userId);
		order.setOrderDate(dateTimeString);
		orderrepo.save(order);

	}

	@GetMapping("/myorders/{userid}")
	public List<Orders> getOrderHistory(@PathVariable("userid") int userId) {
		return orderrepo.findByUserId(userId);
	}
}
