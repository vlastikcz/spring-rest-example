package com.github.vlastikcz.springrestexample.service;

import java.util.Optional;
import java.util.UUID;

import com.github.vlastikcz.springrestexample.domain.UserFeedback;

public interface UserFeedbackService {
    UserFeedback create(UserFeedback userFeedback);

    Iterable<UserFeedback> findAll();

    Iterable<UserFeedback> findByName(String name);

    Optional<UserFeedback> findById(UUID id);
}
