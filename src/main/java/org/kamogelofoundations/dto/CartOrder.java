package org.kamogelofoundations.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CartOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String productName;

	private double productPrice;

	private double cartGrandTotal;
	@OneToOne
	private Cart cart;

	@ManyToMany(cascade = CascadeType.ALL)
	// @JoinTable(name="user_role", joinColumns=@JoinColumn(name="user_id"),
	// inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Product> products;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User customer;

	// @Column(nullable = false)
	private Date date = new Date();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getCartGrandTotal() {
		return cartGrandTotal;
	}

	public void setCartGrandTotal(double cartGrandTotal) {
		this.cartGrandTotal = cartGrandTotal;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean equals(Object obj) {
		return cart.equals(obj);
	}

	public double getGrandTotal() {
		return cart.getGrandTotal();
	}

	public void setGrandTotal(double grandTotal) {
		cart.setGrandTotal(grandTotal);
	}

	public User getUser() {
		return cart.getUser();
	}

	public void setUser(User user) {
		cart.setUser(user);
	}

	public Set<CartLine> getCartlines() {
		return cart.getCartlines();
	}

	public Product getProduct() {
		return cart.getProduct();
	}

	public int hashCode() {
		return cart.hashCode();
	}

	public void setCartlines(Set<CartLine> cartlines) {
		cart.setCartlines(cartlines);
	}

	public void setProduct(Product product) {
		cart.setProduct(product);
	}

	public String toString() {
		return cart.toString();
	}

}
