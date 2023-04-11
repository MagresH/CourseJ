package com.example.coursej.user;


import com.example.coursej.config.SecurityUtils;
import com.example.coursej.course.CourseController;
import com.example.coursej.enrollment.EnrollmentController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "UserController", description = "APIs for managing user resources")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Retrieves all users.")
    public ResponseEntity<CollectionModel<UserDTO>> getAllUsers() {
        var users = userService.findAllUsers();
        var usersDto = userMapper.toDTOList(users);

        usersDto.forEach(user -> {
            Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();
            Link enrollmentsLink = WebMvcLinkBuilder.linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(user.getId())).withRel("enrollments");
            Link coursesLink = WebMvcLinkBuilder.linkTo(methodOn(CourseController.class).getCoursesByUserId(user.getId())).withRel("courses");

            user.add(selfLink, coursesLink, enrollmentsLink);
        });
        Link link = linkTo(UserController.class).withSelfRel();
        CollectionModel<UserDTO> result = CollectionModel.of(usersDto, link);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get user by ID", description = "Retrieves information about a user by their ID.")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {

        var user = userService.getUserById(id);
        var userDto = userMapper.toDTO(user);

        Link enrollmentsLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(user.getId())).withRel("enrollments");
        Link coursesLink = linkTo(methodOn(CourseController.class).getCoursesByUserId(user.getId())).withRel("courses");
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();
        Link selfAllLink = linkTo(UserController.class).withRel(selfLink.getRel());

        userDto.add(selfLink, selfAllLink, enrollmentsLink, coursesLink);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get current user information", description = "Retrieves information about the currently authenticated user.")
    public ResponseEntity<UserDTO> getMe() {

        var user = userService.getUserByEmail(SecurityUtils.getPrincipal().getEmail());
        var userDTO = userMapper.toDTO(user);

        Link enrollmentsLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(user.getId())).withRel("enrollments");
        Link coursesLink = linkTo(methodOn(CourseController.class).getCoursesByUserId(user.getId())).withRel("courses");
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel();
        Link selfAllLink = linkTo(UserController.class).withRel(selfLink.getRel());

        userDTO.add(selfLink, selfAllLink, enrollmentsLink, coursesLink);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new user", description = "Adds a new user to the system.")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDto) {

        var user = userMapper.toEntity(userDto);
        userService.addUser(user);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update user by ID", description = "Updates an existing user by ID.")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        var user =userMapper.toEntity(userDTO);
        userService.updateUser(user);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by ID", description = "Deletes an existing user by ID.")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
