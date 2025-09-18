package com.example.SkinCare.service;

import com.example.SkinCare.model.User;
import com.example.SkinCare.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 사용자(User) 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
// 이 클래스가 스프링의 서비스(Service) 계층 컴포넌트임을 나타냅니다.
@Service
// 이 클래스의 모든 public 메소드는 하나의 트랜잭션(Transaction) 안에서 실행됩니다.
// 메소드 내의 모든 DB 작업이 성공적으로 완료되어야 최종 반영(commit)되고,
// 하나라도 실패하면 모든 작업을 이전 상태로 되돌립니다(rollback). 데이터의 정합성을 보장합니다.
@Transactional
public class UserService {

    // 데이터베이스와 상호작용하는 UserRepository에 대한 의존성입니다.
    private final UserRepository userRepository;

    /**
     * 생성자를 통해 UserRepository의 의존성을 주입(DI)받습니다.
     * 
     * @param userRepository 주입받을 리포지토리 객체
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 새로운 사용자를 데이터베이스에 저장합니다. (회원가입)
     * 
     * @param user 저장할 User 객체
     */
    public void saveUser(User user) {
        try {
            // JpaRepository의 save 메소드를 호출하여 데이터를 저장합니다.
            userRepository.save(user);
        } catch (Exception e) {
            // DB 저장 중 예외가 발생하면 콘솔에 에러를 출력합니다.
            e.printStackTrace();
            // 예외를 다시 던져서 호출한 쪽(컨트롤러 등)에서 에러를 인지할 수 있도록 합니다.
            throw new RuntimeException("회원가입 저장 중 오류 발생");
        }
    }

    /**
     * 데이터베이스에 저장된 모든 사용자 목록을 조회합니다.
     * 
     * @return 모든 User 객체 리스트
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * ID를 기준으로 특정 사용자를 데이터베이스에서 삭제합니다.
     * 
     * @param id 삭제할 사용자의 고유 ID
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * ID를 기준으로 특정 사용자를 조회합니다.
     * 
     * @param id 조회할 사용자의 고유 ID
     * @return 조회된 User 객체를 Optional로 감싸서 반환 (결과가 없을 수도 있음)
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 사용자 아이디(username)로 특정 사용자를 조회합니다.
     * 
     * @param username 조회할 사용자의 아이디
     * @return 조회된 User 객체 (결과가 없으면 null을 반환할 수 있음)
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
