package com.in28minutes.rest.webservices.restwebservices.persons;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restwebservices.jpa.PersonRepository;
import com.in28minutes.rest.webservices.restwebservices.jpa.PostRepository;
import com.in28minutes.rest.webservices.restwebservices.post.Post;

import jakarta.validation.Valid;

@RestController
public class PersonJpaResource {
	
	private PersonRepository personRepository;
	
	private PostRepository postRepository;
	
	
	
	public PersonJpaResource(PersonRepository personRepository,PostRepository postRepository) {
		this.personRepository=personRepository;
		this.postRepository=postRepository;
	}
	//get all users
	@GetMapping("/jpa/persons")
	public List<Person> retrieveAllPersons(){
		return personRepository.findAll();
	}
	//get all posts
	@GetMapping("/jpa/persons/{id}/posts")
	public List<Post> retrieveAllPosts(@PathVariable int id) {
		Optional<Person> person=personRepository.findById(id);
		
		if(person.isEmpty())
			throw new UserNotFoundException("id:"+id);
		return person.get().getPosts();
	}
	
	//delete a person	
	@DeleteMapping("/jpa/persons/{id}")
	public void deletePerson(@PathVariable int id) {
		personRepository.deleteById(id);
	}
	
	//delete a post of a person
	
	
	@DeleteMapping("/jpa/persons/{person_id}/posts/{post_id}")
	public void deletePost(@PathVariable int person_id,@PathVariable int post_id) {
		Optional<Person> personOp=personRepository.findById(person_id);
		
		if(personOp.isEmpty())
			throw new UserNotFoundException("id:"+person_id);
		Optional<Post> postOp=postRepository.findById(post_id);
		if(postOp.isEmpty())
			throw new PostNotFoundException("post id:"+post_id);
		postRepository.deleteById(post_id);		
	}
	
	
	
	// get person
	@GetMapping("/jpa/persons/{id}")
	public EntityModel<Person> retrieveUser(@PathVariable int id) {
		Optional<Person> person=personRepository.findById(id);
		
		if(person.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		EntityModel<Person> entityModel = EntityModel.of(person.get());
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllPersons());
		entityModel.add(link.withRel("all-persons"));
		
		return entityModel;
	}
	
	//get a post
	
	@GetMapping("/jpa/persons/{person_id}/posts/{post_id}")
	public EntityModel<Post> retrieveUser(@PathVariable int person_id,@PathVariable int post_id) {
		Optional<Person> personOp=personRepository.findById(person_id);
		
		if(personOp.isEmpty())
			throw new UserNotFoundException("id:"+person_id);
		Optional<Post> postOp=postRepository.findById(post_id);
		if(postOp.isEmpty())
			throw new PostNotFoundException("post id:"+post_id);
		Post post=postOp.get();
		EntityModel<Post> entityModel = EntityModel.of(post);
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllPosts(person_id));
		entityModel.add(link.withRel("all-posts"));
		
		return entityModel;
	}
	
	//add person
	@PostMapping("/jpa/persons")
	public ResponseEntity<Person> createUser(@Valid @RequestBody Person person){
		
		Person savedPerson=personRepository.save(person);
		
		
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPerson.getId()).toUri();
				
		return ResponseEntity.created(location).build();
	}
	
	// add post
	@PostMapping("/jpa/persons/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id,@Valid @RequestBody Post post) {
		Optional<Person> person=personRepository.findById(id);
		
		if(person.isEmpty())
			throw new UserNotFoundException("id:"+id);
		post.setPerson(person.get());
		
		Post savedPost=postRepository.save(post);
		
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
		
	}
	
	
	
	
}
