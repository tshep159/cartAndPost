package org.kamogelofoundations.repository;

import java.util.List;

import org.kamogelofoundations.dto.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findBySlug(String slug);

	Product findBySlugAndIdNot(String slug, int id);

	Page<Product> findAll(Pageable pageable);

	List<Product> findAllByCategoryId(int categoryId);

	long countByCategoryId(String categoryId);
	
	
	
	
	
	



	
	// business methods

//@Query("FROM Product WHERE active = true ORDER BY " + param + " DESC")
//List<Product> getProductsByParam(String param, int count);	


@Query("FROM Product WHERE active = :active")
List<Product> listActiveProducts();	

@Query("FROM Product WHERE active = :active AND categoryId = :categoryId")
List<Product> listActiveProductsByCategory(int categoryId);

@Query("FROM Product WHERE active = :active ORDER BY id")
List<Product> getLatestActiveProducts(int count);
	
	
	
}
