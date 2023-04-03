package com.example.coursej.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user){
        return userRepository.save(user);
    }
    public User updateUser(User user){
        return userRepository.save(user);
    }
    public void deleteUser(Long id){userRepository.deleteById(id);}
    public User getUserById(Long id) {
        return userRepository.getUserById(id)
                .orElseThrow(() -> new IllegalStateException("User with id " + id + " does not exist"));
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}
