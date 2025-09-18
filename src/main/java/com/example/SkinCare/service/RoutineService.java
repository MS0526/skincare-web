package com.example.SkinCare.service;

import com.example.SkinCare.model.Routine;
import com.example.SkinCare.model.User;
import com.example.SkinCare.repository.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 스킨케어 루틴(Routine) 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
// 이 클래스가 스프링의 서비스(Service) 계층 컴포넌트임을 나타냅니다.
@Service
public class RoutineService {

    // 데이터베이스와 상호작용하는 RoutineRepository에 대한 의존성입니다.
    private final RoutineRepository routineRepository;

    /**
     * 생성자를 통해 RoutineRepository의 의존성을 주입(DI)받습니다.
     * 
     * @param routineRepository 주입받을 리포지토리 객체
     */
    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    /**
     * 새로운 루틴을 데이터베이스에 저장하거나 기존 루틴을 업데이트합니다.
     * 
     * @param routine 저장할 Routine 객체
     */
    public void saveRoutine(Routine routine) {
        // JpaRepository의 save 메소드를 호출하여 데이터를 저장합니다.
        routineRepository.save(routine);
    }

    /**
     * 특정 사용자가 작성한 모든 루틴을 조회합니다.
     * 
     * @param user 루틴을 조회할 User 객체
     * @return 해당 사용자의 모든 Routine 객체 리스트
     */
    public List<Routine> getRoutinesByUser(User user) {
        // 사용자(User) 객체를 기준으로 루틴을 검색합니다.
        return routineRepository.findByUser(user);
    }

    /**
     * ID를 기준으로 특정 루틴을 데이터베이스에서 삭제합니다.
     * 
     * @param id 삭제할 루틴의 고유 ID
     */
    public void deleteRoutine(Long id) {
        // 해당 ID를 가진 데이터를 삭제합니다.
        routineRepository.deleteById(id);
    }
}
