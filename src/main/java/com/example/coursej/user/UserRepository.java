package com.example.coursej.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    Optional<User> getUserByEmail(String email);

    Optional<User> findByEmail(String email);


    @Query("select u from User u where upper(u.firstName) like CONCAT('%',UPPER(?1),'%') and upper(u.lastName) like CONCAT('%',UPPER(?2),'%') and upper(u.email) like CONCAT('%',UPPER(?3),'%')")
    Page<User> findUsersByFirstNameLikeAndLastNameLikeAndEmailLikeIgnoreCase(String firstNameFilter, String lastNameFilter, String emailFilter, Pageable pageable);

}
