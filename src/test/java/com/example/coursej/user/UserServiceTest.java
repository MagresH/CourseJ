package com.example.coursej.user;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user1, user2;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .password("password")
                .build();
        user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@doe.com")
                .password("password")
                .build();
    }

    @Test
    void testFindAllUsers() {
        // Given
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // When
        List<User> actualUsers = userService.findAllUsers();

        // Then
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    void testAddUser() {
        // Given
        User newUser = User.builder()
                .id(3L)
                .firstName("New")
                .lastName("User")
                .email("new@user.com")
                .password("password")
                .build();
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(newUser)).thenReturn(newUser);

        // When
        User addedUser = userService.addUser(newUser);

        // Then
        assertNotNull(addedUser);
        assertEquals(newUser.getFirstName(), addedUser.getFirstName());
        assertEquals(newUser.getLastName(), addedUser.getLastName());
        assertEquals(newUser.getEmail(), addedUser.getEmail());
        assertEquals(newUser.getPassword(), addedUser.getPassword());
        assertEquals("encryptedPassword", addedUser.getPassword());
        verify(userRepository).save(newUser);
    }

    @Test
    void testUpdateUser() {
        // Given
        User updatedUser = User.builder()
                .id(1L)
                .firstName("Updated")
                .lastName("User")
                .email("updated@user.com")
                .password("password")
                .build();
        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // When
        User actualUser = userService.updateUser(updatedUser);

        // Then
        assertEquals(updatedUser, actualUser);
        verify(userRepository).save(updatedUser);
    }

    @Test
    void testDeleteUser() {
        // Given
        Long userId = 1L;

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository).deleteById(userId);
    }

    @Test
    void testGetUserById() {
        // Given
        Long userId = 1L;
        when(userRepository.getUserById(userId)).thenReturn(Optional.of(user1));

        // When
        User actualUser = userService.getUserById(userId);

        // Then
        assertEquals(user1, actualUser);
    }

    @Test
    void testGetUserByEmail() {
        // Given
        String email = "johndoe@example.com";
        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user1));

        // When
        User actualUser = userService.getUserByEmail(email);

        // Then
        assertEquals(user1, actualUser);
    }
    @Test
    void testFindAllUsersEmpty() {
        // Given
        List<User> expectedUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // When
        List<User> actualUsers = userService.findAllUsers();

        // Then
        assertEquals(expectedUsers, actualUsers);
    }
    @Test
    void testAddUserThrowsException() {
        // Given
        User newUser = User.builder()
                .id(3L)
                .firstName("New")
                .lastName("User")
                .email("new@user.com")
                .password("password")
                .build();
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encryptedPassword");
        when(userRepository.save(newUser)).thenThrow(new RuntimeException("Database error"));

        // When/Then
        assertThrows(RuntimeException.class, () -> userService.addUser(newUser));
    }
    @Test
    void testUpdateUserThrowsException() {
        // Given
        User updatedUser = User.builder()
                .id(1L)
                .firstName("Updated")
                .lastName("User")
                .email("updated@user.com")
                .password("password")
                .build();
        when(userRepository.save(updatedUser)).thenThrow(new RuntimeException("Database error"));

        // When/Then
        assertThrows(RuntimeException.class, () -> userService.updateUser(updatedUser));
    }
}
