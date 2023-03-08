package com.example.coursej.repository;

import com.example.coursej.model.user.Admin;
import org.springframework.stereotype.Repository;

@Repository

public interface AdminRepository extends UserRepository<Admin> {
}
