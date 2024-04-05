package com.in28minutes.rest.webservices.restwebservices.persons;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class PersonDaoService {


	private static List<Person> persons=new ArrayList<>();
	
	private static int count=0;
	
	static {
		persons.add(new Person(++count,"ToxicDev",LocalDate.now().minusYears(30)));
		persons.add(new Person(++count,"ComplexDev",LocalDate.now().minusYears(35)));
		persons.add(new Person(++count,"simpleDev",LocalDate.now().minusYears(20)));
	}
	
	public List<Person> findAll(){
		return persons;
	}
	
	public Person save(Person person) {
		person.setId(++count);
		persons.add(person);
		return person;
	}
	
	public Person findOne(int id) {
		
		Predicate<? super Person> predicate = person -> person.getId().equals(id);
		return persons.stream().filter(predicate).findFirst().orElse(null);
	}

	public void deleteById(int id) {
		Predicate<? super Person> predicate = person -> person.getId().equals(id);
		persons.removeIf(predicate);
	}
}
