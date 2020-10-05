package org.kamogelofoundations.dto;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "grand_total")
	private double grandTotal;
	@OneToMany(mappedBy = "cart")
	private Set<CartLine> cartlines;

	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private Product product;

	
	private boolean saved;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}

	// linking the cart with a user
	@OneToOne
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<CartLine> getCartlines() {
		return cartlines;
	}

	public void setCartlines(Set<CartLine> cartlines) {
		this.cartlines = cartlines;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}

	
}
