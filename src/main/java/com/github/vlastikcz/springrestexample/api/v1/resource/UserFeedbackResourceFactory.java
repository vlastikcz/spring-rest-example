package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.util.Objects;

import com.github.vlastikcz.springrestexample.domain.UserFeedback;

/**
 * Factory for the {@link UserFeedbackResource}
 */
final class UserFeedbackResourceFactory {
    /**
     * Create new {@link UserFeedbackResource} from {@link UserFeedback} instance
     *
     * @param userFeedback
     *         {@link UserFeedback} instance to be converted, cannot be null
     * @return a new {@link UserFeedbackResource}
     */
    static UserFeedbackResource fromUserFeedback(UserFeedback userFeedback) {
        Objects.requireNonNull(userFeedback, "'userFeedback' cannot be null");
        final UserFeedbackResource userFeedbackResource = new UserFeedbackResource(
                userFeedback.getName(), userFeedback.getMessage(), userFeedback.getSubmittedOn()
        );
        return userFeedbackResource;
    }
}
