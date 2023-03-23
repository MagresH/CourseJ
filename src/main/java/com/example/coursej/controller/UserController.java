package com.example.coursej.controller;


import com.example.coursej.model.User;
import com.example.coursej.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionModel<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();

        users.forEach(user -> {
            Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();
            Link enrollmentsLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(user.getId())).withRel("enrollments");
            Link coursesLink = linkTo(methodOn(CourseController.class).getCoursesByUserId(user.getId())).withRel("courses");

            user.add(selfLink, coursesLink, enrollmentsLink);
        });
        Link link = linkTo(UserController.class).withSelfRel();
        CollectionModel<User> result = CollectionModel.of(users, link);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);

        Link enrollmentsLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(user.getId())).withRel("enrollments");
        Link coursesLink = linkTo(methodOn(CourseController.class).getCoursesByUserId(user.getId())).withRel("courses");
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();
        Link selfAllLink = linkTo(UserController.class).withRel(selfLink.getRel());

        user.add(selfLink, selfAllLink, enrollmentsLink, coursesLink);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/me")
    public ResponseEntity<User> getMe(Principal principal) {
        System.out.println(principal.getName());
        User user = userService.getUserByEmail(principal.getName()).get();
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        Link enrollmentsLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(user.getId())).withRel("enrollments");
        Link coursesLink = linkTo(methodOn(CourseController.class).getCoursesByUserId(user.getId())).withRel("courses");
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();
        Link selfAllLink = linkTo(UserController.class).withRel(selfLink.getRel());

        user.add(selfLink, selfAllLink, enrollmentsLink, coursesLink);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
