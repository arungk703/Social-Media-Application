package com.restfulwebservice.socialmediaapplication.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.restfulwebservice.socialmediaapplication.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	

	public UserJpaResource(UserRepository repository, PostRepository postRepository) {
		this.repository = repository;
		this.postRepository = postRepository;
	}

	private UserRepository repository;
	private PostRepository postRepository;

	
	@GetMapping("jpa/users")
	public List<User> retriveAllUsers(){
		return repository.findAll();
	}
	
	@GetMapping("jpa/users/{id}")
	public User retriveUserById(@PathVariable int id) {
		
		Optional<User> user = repository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id: " + id);
		
		return user.get();
	}
	
	@PostMapping("jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		
		repository.deleteById(id);
		
	}
	
	@GetMapping("jpa/users/{id}/posts")
	public List<Post> retrivePostsForUser(@PathVariable int id) {
		
		Optional<User> user = repository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id: " + id);
		
		return user.get().getPosts();
	}
	
	@PostMapping("jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = repository.findById(id);
		
		if(user.isEmpty())
			throw  new UserNotFoundException("id: " + id);
		
		post.setUser(user.get());
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("jpa/users/{id}/posts/{postId}")
	public Post retriveSpecificPostForUser(@PathVariable int id, @PathVariable int postId) {
		
		Optional<User> user = repository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id: " + id);
		
		Predicate<? super Post> predicate = post -> post.getId() == postId;
		Optional<Post> post = user.get().getPosts().stream().filter(predicate).findFirst();
		
		if(post.isEmpty())
			throw new UserNotFoundException("PostId: " + postId);
		
		return post.get();
		
	}

}
