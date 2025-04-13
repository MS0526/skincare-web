package com.example.SkinCare.service;

import com.example.SkinCare.model.Routine;
import com.example.SkinCare.model.User;
import com.example.SkinCare.repository.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineRepository routineRepository;

    public RoutineService(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    public void saveRoutine(Routine routine) {
        routineRepository.save(routine);
    }

    public List<Routine> getRoutinesByUser(User user) {
        return routineRepository.findByUser(user);
    }

    public void deleteRoutine(Long id) {
        routineRepository.deleteById(id);
    }
}
