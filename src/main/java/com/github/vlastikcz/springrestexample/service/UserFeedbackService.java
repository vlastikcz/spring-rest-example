package com.github.vlastikcz.springrestexample.service;

import java.util.Optional;
import java.util.UUID;

import com.github.vlastikcz.springrestexample.domain.UserFeedback;

/**
 * Business logic for the {@link UserFeedback} entity
 */
public interface UserFeedbackService {

    /**
     * Save new {@link UserFeedback}
     *
     * @param userFeedback
     *         entity to be saved, cannot be {@code null}
     * @return saved entity
     * @throws NullPointerException
     *         if any of the required parameters is {@code null}
     */
    UserFeedback save(UserFeedback userFeedback);

    /**
     * Find all existing {@link UserFeedback} items
     *
     * @return all items, may be empty
     */
    Iterable<UserFeedback> findAll();

    /**
     * Find all existing {@link UserFeedback} items by name (contains-type match).
     *
     * @param name
     *         requested name, cannot be {@code null}
     * @return all matching items, may be empty
     * @throws NullPointerException
     *         if any of the required parameters is {@code null}
     */
    Iterable<UserFeedback> findByNameContainsCaseInsensitive(String name);

    /**
     * Find single {@link UserFeedback} by it's ID
     *
     * @param id
     *         requested ID, cannot be {@code null}
     * @return optional with matching {@link UserFeedback}, may be empty
     * @throws NullPointerException
     *         if any of the required parameters is {@code null}
     */
    Optional<UserFeedback> findById(UUID id);
}
