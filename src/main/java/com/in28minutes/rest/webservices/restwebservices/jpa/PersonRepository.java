package com.in28minutes.rest.webservices.restwebservices.jpa;



import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restwebservices.persons.Person;

public interface PersonRepository extends JpaRepository<Person,Integer>{

}
