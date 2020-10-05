package org.kamogelofoundations.controller;

import java.util.List;

import org.kamogelofoundations.dto.CartLine;
import org.kamogelofoundations.dto.CartOrder;
import org.kamogelofoundations.dto.Post;
import org.kamogelofoundations.dto.Product;
import org.kamogelofoundations.dto.User;
import org.kamogelofoundations.repository.CartLineRepository;
import org.kamogelofoundations.repository.CartOrderRepository;
import org.kamogelofoundations.repository.PostRepository;
import org.kamogelofoundations.repository.ProductRepository;
import org.kamogelofoundations.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/json/data")
public class JsonDataController {

	@Autowired
	private ProductRepository productDAO;
	@Autowired
	private CartLineRepository cartLineRepo;
	@Autowired
	private PostRepository postDAO;;
	@Autowired
	UserService us;
	@Autowired
	CartOrderRepository co;
	
	
	
	

	@RequestMapping("/user/{id}/posts")
	@ResponseBody
	public List<Post> getPostsByUser(@PathVariable long id) {
		
		User user = us.get(id);
		return postDAO.findAllByAuthorOrderByDateDesc(user);
				
	}
	
	
	
	

	@RequestMapping("/cart/{id}/products")
	@ResponseBody
	public List<CartLine> getCartLines(@PathVariable int id) {
		
		return cartLineRepo.findCartLineByCartId(id);
				
	}
	
	
	
	
	
	@RequestMapping("/admin/all/products")
	@ResponseBody
	public List<Product> getAllProductsList() {		
		return productDAO.findAll();
				
	}	
	
	
	@RequestMapping("/all/orders")
	@ResponseBody
	public List<CartOrder> getAllorders() {		
		return co.findAll();
				
	}	
	
	@RequestMapping("/all/products")
	@ResponseBody
	public List<Product> getAllProducts() {
		
		return productDAO.findAll();
				
	}
	
	@RequestMapping("/category/{id}/products")
	@ResponseBody
	public List<Product> getProductsByCategory(@PathVariable int id) {
		
		return productDAO.listActiveProductsByCategory(id);
				
	}
	
	/*
	@RequestMapping("/mv/products")
	@ResponseBody
	public List<Product> getMostViewedProducts() {		
		return productDAO.getProductsByParam("views", 5);				
	}
		*/
	/*@RequestMapping("/mp/products")
	@ResponseBody
	public List<Product> getMostPurchasedProducts() {		
		return productDAO.getProductsByParam("purchases", 5);				
	}*/
	
	

}
