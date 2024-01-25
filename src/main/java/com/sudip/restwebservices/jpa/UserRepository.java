package com.sudip.restwebservices.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import com.sudip.restwebservices.user.User;
public interface UserRepository extends JpaRepository<User, Integer> {


}
