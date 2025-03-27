package com.example.SkinCare.repository;

import com.example.SkinCare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자 이름으로 사용자 조회
    User findByUsername(String username); // username으로 사용자 조회
}
