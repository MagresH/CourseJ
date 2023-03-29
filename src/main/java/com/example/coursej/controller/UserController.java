package com.example.coursej.controller;


import com.example.coursej.config.SecurityUtils;
import com.example.coursej.model.User;
import com.example.coursej.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "UserController", description = "APIs for managing user resources")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Retrieves all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all users returned successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID", description = "Retrieves information about a user by their ID.")
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
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get current user information", description = "Retrieves information about the currently authenticated user.")
    public ResponseEntity<User> getMe() {
        User user = userService.getUserByEmail(SecurityUtils.getPrincipal().getEmail());

        Link enrollmentsLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(user.getId())).withRel("enrollments");
        Link coursesLink = linkTo(methodOn(CourseController.class).getCoursesByUserId(user.getId())).withRel("courses");
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();
        Link selfAllLink = linkTo(UserController.class).withRel(selfLink.getRel());

        user.add(selfLink, selfAllLink, enrollmentsLink, coursesLink);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new user", description = "Adds a new user to the system.")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user by ID", description = "Updates an existing user by ID.")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by ID", description = "Deletes an existing user by ID.")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
