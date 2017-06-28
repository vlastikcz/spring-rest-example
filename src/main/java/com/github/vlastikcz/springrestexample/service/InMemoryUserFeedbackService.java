package com.github.vlastikcz.springrestexample.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.vlastikcz.springrestexample.domain.UserFeedback;

@Service
public class InMemoryUserFeedbackService implements UserFeedbackService {
    final List<UserFeedback> userFeedbacks = new ArrayList<>();

    @Override
    public UserFeedback create(UserFeedback userFeedback) {
        final UserFeedback savedUserFeedback = userFeedback.toUserFeedbackBuilder()
                .setId(UUID.randomUUID())
                .setSubmittedOn(Instant.now())
                .createUserFeedback();
        userFeedbacks.add(savedUserFeedback);
        return savedUserFeedback;
    }

    @Override
    public Iterable<UserFeedback> findAll() {
        userFeedbacks.add(new UserFeedback(UUID.randomUUID(), "name", "msg", Instant.now()));
        return userFeedbacks;
    }

    @Override
    public Iterable<UserFeedback> findByName(String name) {
        return userFeedbacks.stream().filter(f -> f.nameMatches(name)).collect(Collectors.toList());
    }

    @Override
    public Optional<UserFeedback> findById(UUID id) {
        return userFeedbacks.stream().filter(f -> f.getId().equals(id)).findAny();
    }

}
