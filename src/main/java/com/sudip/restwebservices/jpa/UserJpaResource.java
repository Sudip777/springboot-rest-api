package com.sudip.restwebservices.jpa;


import com.sudip.restwebservices.user.Post;
import com.sudip.restwebservices.user.User;
import com.sudip.restwebservices.user.UserDaoService;
import com.sudip.restwebservices.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserJpaResource {
//    private UserDaoService service;
    private UserRepository repository;
    private PostRepository postRepository;

    // Constructor Injection
//    UserDaoService service  this.service = service;

    public UserJpaResource( UserRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }
    // GET /users
    @GetMapping(path = "/jpa/users")
    public List<User> retrieveAllUsers() {
        return repository.findAll();
    }
    // GET single User
    @GetMapping(path = "/jpa/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = repository.findById(id);

        // Handle Default Exception
        if (user.isEmpty()) {
            throw new UserNotFoundException("id" + id);
        }

            // http://localhost:8080/users
            // Entity Model
            // WebMvcLinkBuilder
            EntityModel<User> entityModel = EntityModel.of(user.get());
            WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
            entityModel.add(link.withRel("all-users"));
            return entityModel;
    }

    // Create new User
    @PostMapping(path = "/jpa/users")
    public ResponseEntity<Object> createUser( @Valid @RequestBody User user) {

//        service.save(user);
//        return ResponseEntity.created(null).build(); //This only changes status code to 201

        // Thinking from the consumer perspective also returning uri of changed user
        // location header - users/4 => /users /{id} user.getId()
        User savedUser = repository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }

    // Delete User By id
    // GET single User
    @DeleteMapping(path = "/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }

    // Get post User By id
    // GET post
    @GetMapping(path = "/jpa/users/{id}/posts")
    public List<Post> getPostsForUser(@PathVariable int id) {

        Optional<User> user = repository.findById(id);
        // Handle Default Exception
        if (user.isEmpty())
            throw new UserNotFoundException("id" + id);
    return   user.get().getPosts();
    }

    @PostMapping(path = "/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post post) {

        Optional<User> user = repository.findById(id);
        // Handle Default Exception
        if (user.isEmpty())
            throw new UserNotFoundException("id" + id);

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

//    @PostMapping(path = "/jpa/users/{id}/posts/{id}")
//    public ResponseEntity<Object> getPostsForUserById(@PathVariable int id, @Valid @RequestBody Post post) {
//
//        Optional<User> user = repository.findById(id);
//        // Handle Default Exception
//        if (user.isEmpty())
//            throw new UserNotFoundException("id" + id);
//
//        Optional <Post> posts = postRepository.findById(id);
//
//
//        return ResponseEntity.ok(post.get());
//    }


}