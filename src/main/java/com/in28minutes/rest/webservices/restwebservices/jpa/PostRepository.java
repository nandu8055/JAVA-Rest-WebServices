package com.in28minutes.rest.webservices.restwebservices.jpa;



import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.rest.webservices.restwebservices.post.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{

}
