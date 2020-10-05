package org.kamogelofoundations.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//import lombok.Data;

@Entity
@Table(name = "products")
//@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 2, message = "Name must be at least 2 charachters long")
	private String name;

	private String slug;

	@Size(min = 5, message = "Description must be at least 5 charachters long")
	private String description;

	private String image;

	// @Pattern(regexp = "^[0-9]+([.][0-9]{1,2})?", message = "Expected format: 5,
	// 5.99, 15, 15.99")
	// private double price;

	@Column(name = "category_id")
	@Pattern(regexp = "^[1-9][1-9]*", message = "Please choose a category")
	private String categoryId;

	@Column(name = "unit_price")
	@Min(value = 1, message = "Please select at least one value!")
	private double unitPrice;
	private int quantity;

	@Column(name = "created_at", updatable = false)
	// @CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
//	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
