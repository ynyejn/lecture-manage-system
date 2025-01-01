package com.example.lecturemanagesystem.infrastructure.repository;

import com.example.lecturemanagesystem.domain.entity.User;
import com.example.lecturemanagesystem.domain.repository.IUserRepository;
import com.example.lecturemanagesystem.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements IUserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }
    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
