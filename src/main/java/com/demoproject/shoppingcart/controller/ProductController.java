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
import java.util.List;

import javax.transaction.Transactional;

import com.demoproject.shoppingcart.model.Cart;
import com.demoproject.shoppingcart.model.Product;
import com.demoproject.shoppingcart.model.Wishlist;
import com.demoproject.shoppingcart.repository.CartRepository;
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

	@GetMapping("/products")
	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	// CartController
//	@PostMapping("/cart")
//	public void addProductToCart(@RequestBody Product product){
//		Cart cart = new Cart();
//		System.out.println(cartrepo.findByProductId(product.getId()));
//		if(cartrepo.findByProductId(product.getId())==null) {
//			cart.setQty(1L);
//			cart.setProductId(product.getId());
//			cart.setProductName(product.getName());
//			cart.setPrice(product.getPrice());
//			cartrepo.save(cart);
//		}
//		else {
//			cart=cartrepo.findByProductId(product.getId());
//			cart.setQty(cart.getQty()+1);
//			cartrepo.save(cart);
//		}
//		
//	}

	// CartController
//	@GetMapping("/cartitems")
//	public List<Cart> getAllCartItems(){
//		return cartrepo.findAll();
//	}

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

	// WishlistController
	@PostMapping("/wishlist")
	public void addToWishlist(@RequestBody Product product) {
		Wishlist ob = new Wishlist();
		ob.setName(product.getName());
		ob.setPrice(product.getPrice());
		ob.setProductId(product.getId());
		wishrepo.save(ob);
	}

	@GetMapping("/wishlist")
	public List<Wishlist> getWishlist() {
		return wishrepo.findAll();
	}

	@DeleteMapping("/wishlist/{id}")
	public void removeFromWishlist(@PathVariable("id") long pid) {
		wishrepo.deleteById(pid);
	}

}
