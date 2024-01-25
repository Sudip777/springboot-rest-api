package com.sudip.restwebservices.user;


import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {
    private UserDaoService service;

    public UserResource(UserDaoService service) {
        this.service = service;
    }

    // GET /users
    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // GET single User
    @GetMapping(path = "/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);

        // Handle Default Exception
        if (user == null) {
            throw new UserNotFoundException("id" + id);
        }

            // http://localhost:8080/users
            // Entity Model
            // WebMvcLinkBuilder
            EntityModel<User> entityModel = EntityModel.of(user);
            WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
            entityModel.add(link.withRel("all-users"));
            return entityModel;
    }

    // Create new User
    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser( @Valid @RequestBody User user) {

//        service.save(user);
//        return ResponseEntity.created(null).build(); //This only changes status code to 201

        // Thinking from the consumer perspective also returning uri of changed user
        // location header - users/4 => /users /{id} user.getId()
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();


    }

    // Delete User By id
    // GET single User
    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable int id) {
        service.deleteById(id);
    }
}