package org.kamogelofoundations.repository;

import java.util.List;

import org.kamogelofoundations.dto.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Category findByName(String name);
	
	List<Category> findAllByOrderBySortingAsc();

	Category findBySlug(String slug);
	
}
