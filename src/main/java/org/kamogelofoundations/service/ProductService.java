package org.kamogelofoundations.service;

import java.util.List;

import org.kamogelofoundations.dto.Product;

public interface ProductService {

	/**
	 * @param productId
	 * @return
	 */
	Product get(int productId);
	/**
	 * @return
	 */
	List<Product> list();
	/**
	 * @param product
	 * @return
	 */
	Product add(Product product);
	/**
	 * @param product
	 * @return
	 */
	Product update(Product product);
	/**
	 * @param product
	 * @return
	 */
	Product delete(Product product);
	
	//business methods
	/**
	 * @return All active products
	 */
	List<Product> listActiveProducts();
	/**
	 * @param categoryId
	 * @return List of Active Products By Category of a specific id
	 */
	List<Product> listActiveProductsByCategory(int categoryId);
	/**
	 * @param count
	 * @return List of active product
	 */
	List<Product> getLatestActiveProducts(int count);
}
