package com.in28minutes.rest.webservices.restwebservices.persons;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.in28minutes.rest.webservices.restwebservices.post.Post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
public class Person {
	// id,name,birthdate
	
	protected Person() {
		
	}
	
	@Id
	@GeneratedValue
	private Integer id;
	@Size(min=2, message="Name must be atleast 2 characters")
	private String name;
	@Past(message="Birthdate must be in past")
	private LocalDate birthdate;
	
	@OneToMany(mappedBy="person")
	@JsonIgnore
	private List<Post> posts;
	
	public Person(int id, String name, LocalDate birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
	}

	
	
	
	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public LocalDate getBirthdate() {
		return birthdate;
	}




	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}




	public List<Post> getPosts() {
		return posts;
	}




	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}




	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", birthdate=" + birthdate + "]";
	}


	
	
	
}
