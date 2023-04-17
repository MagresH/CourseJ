package com.example.coursej.user;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public Page<User> fetchFilteredAndSortedUsers(String firstNameFilter, String lastNameFilter, String emailFilter, int page, int size,List<String> sortList, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortDirection)));

        return userRepository.findUsersByFirstNameLikeAndLastNameLikeAndEmailLikeIgnoreCase(firstNameFilter, lastNameFilter, emailFilter, pageable);
    }

    public User addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User updateUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {

        List<Sort.Order> sorts = new ArrayList<>();

        Sort.Direction direction;

        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.ASC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
}
