package com.in28minutes.rest.webservices.restwebservices.persons;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;

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

import jakarta.validation.Valid;

@RestController
public class PersonController {
	
	private PersonDaoService service;
	
	public PersonController(PersonDaoService service) {
		this.service=service;
	}
	//get all
	@GetMapping("/persons")
	public List<Person> retrieveAllPersons(){
		return service.findAll();
	}
	
	
//	//findperson by id
//	@GetMapping("/persons/{id}")
//	private Person retrievePerson(@PathVariable int id) {
//		Person person=service.findOne(id);
//		if(person==null) {
//			throw new UserNotFoundException("id:"+id);
//		}
//		return person;
//	}
	
	@DeleteMapping("/persons/{id}")
	public void deletePerson(@PathVariable int id) {
		service.deleteById(id);
	}
	
	
	@GetMapping("/persons/{id}")
	public EntityModel<Person> retrieveUser(@PathVariable int id) {
		Person person=service.findOne(id);
		
		if(person==null)
			throw new UserNotFoundException("id:"+id);
		
		EntityModel<Person> entityModel = EntityModel.of(person);
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).retrieveAllPersons());
		entityModel.add(link.withRel("all-persons"));
		
		return entityModel;
	}
	
	
	//add person
	@PostMapping("/persons")
	public ResponseEntity<Person> createUser(@Valid @RequestBody Person person){
		
		Person savedPerson=service.save(person);
		
		
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPerson.getId()).toUri();
				
		return ResponseEntity.created(location).build();
	}
	
	
	
	
	
	
}
