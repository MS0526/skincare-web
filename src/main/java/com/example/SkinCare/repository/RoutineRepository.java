package com.example.SkinCare.repository;

import com.example.SkinCare.model.Routine;
import com.example.SkinCare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    // 로그인된 유저 기준으로 루틴 목록 조회
    List<Routine> findByUser(User user);
}
