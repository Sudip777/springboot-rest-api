package com.sudip.restwebservices.jpa;


import com.sudip.restwebservices.user.Post;
import com.sudip.restwebservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {


}
