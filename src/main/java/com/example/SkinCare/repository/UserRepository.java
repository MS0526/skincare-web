package com.example.SkinCare.repository;

import com.example.SkinCare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
