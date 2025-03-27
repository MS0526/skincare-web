package com.example.SkinCare.service;

import com.example.SkinCare.model.User;
import com.example.SkinCare.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        System.out.println("!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!");
        try {
            System.out.println("!!!!!!!!!!!!!!!!!!!");
            Thread.sleep(5000);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace(); // 예외 메시지 확인
            throw new RuntimeException("회원가입 저장 중 오류 발생"); // 예외 강제 발생 (이 예외는 롤백됩니다.)
        }
    }

    // 생성자 주입
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 모든 사용자 조회
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ID로 사용자 조회
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 사용자 등록
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
