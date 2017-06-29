package com.github.vlastikcz.springrestexample.service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.vlastikcz.springrestexample.domain.UserFeedback;

@Service
public class InMemoryUserFeedbackService implements UserFeedbackService {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserFeedbackService.class);

    private final List<UserFeedback> userFeedbackList = new CopyOnWriteArrayList<>();
    private final Clock clock;
    private final UuidGeneratorService uuidGeneratorService;

    @Autowired
    public InMemoryUserFeedbackService(Clock clock, UuidGeneratorService uuidGeneratorService) {
        this.clock = clock;
        this.uuidGeneratorService = uuidGeneratorService;
    }

    @Override
    public UserFeedback save(UserFeedback userFeedback) {
        Objects.requireNonNull(userFeedback, "'userFeedback' cannot be null");
        final UserFeedback savedUserFeedback = userFeedback.toUserFeedbackBuilder()
                .setId(uuidGeneratorService.findRandomUuid())
                .setSubmittedOn(Instant.now(clock))
                .createUserFeedback();
        userFeedbackList.add(savedUserFeedback);
        logger.info("new feedback saved: {}", savedUserFeedback);
        return savedUserFeedback;
    }

    @Override
    public Iterable<UserFeedback> findAll() {
        logger.info("find all result: {} items", userFeedbackList.size());
        return userFeedbackList;
    }

    @Override
    public Iterable<UserFeedback> findByNameContainsCaseInsensitive(String name) {
        Objects.requireNonNull(name, "'name' cannot be null");
        final Predicate<UserFeedback> filterByName = filterByNameContainsCaseInsensitive(name);
        final List<UserFeedback> result = userFeedbackList.stream().filter(f -> filterByName.test(f)).collect(Collectors.toList());
        logger.info("find by name result: {} items", result.size());
        return result;
    }

    private static Predicate<UserFeedback> filterByNameContainsCaseInsensitive(String name) {
        final String nameLowerCase = name.toLowerCase();
        return (userFeedback) -> userFeedback.getName().toLowerCase().contains(nameLowerCase);
    }

    @Override
    public Optional<UserFeedback> findById(UUID id) {
        Objects.requireNonNull(id, "'id' cannot be null");
        final Optional<UserFeedback> result = userFeedbackList.stream().filter(f -> f.getId().equals(id)).findAny();
        logger.info("find by ID result: {}", result);
        return result;
    }

}
