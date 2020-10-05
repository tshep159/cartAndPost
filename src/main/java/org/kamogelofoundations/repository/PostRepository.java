package org.kamogelofoundations.repository;

import java.util.List;

import org.kamogelofoundations.dto.Post;
import org.kamogelofoundations.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	/**
	 * Note that the JPQL query will be automatically implemented and mapped to the method findLatest5Posts() 
	 * 	in the service implementation provided by Spring Data. 
	 * Original. @Query("SELECT p FROM posts p LEFT JOIN FETCH p.author ORDER BY p.date DESC")
	 * 
	 * TODO Important !!! use native query set to true or it won't work
	 * 		@Query(value="SELECT p.* FROM posts p Where p.id=:pId ORDER BY p.date DESC", nativeQuery = true)
	 * 
	 * 	IIF you want to use parameters try this definition
	 * 		@Query(value="SELECT p.* FROM posts p Where p.id=:pId ORDER BY p.date DESC", nativeQuery = true)
	 * 		List<Post> findLates5Posts(Pageable pageable, @Param("pId") Long id);
	 * 
	 * @param pageable
	 * @return
	 */	
	//@Query(value="SELECT p.* FROM posts p ORDER BY p.date DESC", nativeQuery = true)	
	//List<Post> findLates5Posts(Pageable pageable);	
	
	
   List<Post>findAllByAuthorOrderByDateDesc(User author);
	
	
	
	
	/*
	@Modifying
	@Transactional
	@Query("DELETE FROM Posts p WHERE p.id = ?1")
	void deleteById(Long id);
	*/
	
	List<Post> findPostsByAuthor(long id);	
	 
}