package com.example.lecturemanagesystem.domain.repository;

import com.example.lecturemanagesystem.domain.entity.User;

import java.util.Optional;

public interface IUserRepository {
    Optional<User> findById(Long notExistUserId);
    User save(User user);
}
