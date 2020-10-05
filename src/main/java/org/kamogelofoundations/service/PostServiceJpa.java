package org.kamogelofoundations.service;

import java.util.List;

import org.kamogelofoundations.dto.Post;
import org.kamogelofoundations.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Implement the PostService and UserService to Use the DB
 * 	Just add new implementations for the UserService and PostService, 
 * 	annotated with @Primary. 
 * 	This will tell the Spring Framework to use these implementations instead of the old stubs. 
 *
 */
@Service
@Primary
public class PostServiceJpa implements PostService {
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public List<Post> findAll() {
		return this.postRepository.findAll();
	}
	@Override
	public Page<Post> findAll(Pageable pageable){
		return this.postRepository.findAll(pageable);
	}
	@Override
	public List<Post> findLatest5() {
		// Create our own query
		return  null; //this.postRepository.findLates5Posts( PageRequest.of(0,5) );
		// Using Streams also worked Descending order
		//return this.postRepository.findAll( PageRequest.of(0, 2) ).stream().sorted( (a,b) -> a.getDate().compareTo(b.getDate()) ).collect(Collectors.toList());
	}
	@Override
	public Post findById(Long id) {
		return this.postRepository.findOne(id);
		// return this.postRepository.getOne(id);
	}
	@Override
	public Post create(Post post) {
		return this.postRepository.save(post);
	}
	@Override
	public Post edit(Post post) {
		return this.postRepository.save(post);
	}
	@Override
	public void deleteById(Long id) {
		
	
	}
	@Override
	public List<Post> getByUser(long id) {
		// TODO Auto-generated method stub
		return postRepository.findPostsByAuthor(id);
	}
}
