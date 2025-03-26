package com.example.SkinCare.repository;

import com.example.SkinCare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 추가적인 쿼리를 작성하려면 메서드를 정의할 수 있음.
    // 예시: Optional<User> findByUsername(String username);
}
