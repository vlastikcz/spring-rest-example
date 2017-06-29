package com.github.vlastikcz.springrestexample.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Core representation of the user feedback
 */
public final class UserFeedback {
    private final UUID id;
    private final String name;
    private final String message;
    private final Instant submittedOn;

    public UserFeedback(UUID id, String name, String message, Instant submittedOn) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.submittedOn = submittedOn;
    }

    /**
     * Convert {@link UserFeedback} to a new {@link UserFeedbackBuilder}
     *
     * @return new builder with populated fields based on the original entity
     */
    public UserFeedbackBuilder toUserFeedbackBuilder() {
        return new UserFeedbackBuilder()
                .setId(this.id)
                .setName(this.name)
                .setMessage(this.message)
                .setSubmittedOn(this.submittedOn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserFeedback that = (UserFeedback) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(message, that.message) &&
                Objects.equals(submittedOn, that.submittedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, message, submittedOn);
    }

    @Override
    public String toString() {
        return "UserFeedback{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", submittedOn=" + submittedOn +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public Instant getSubmittedOn() {
        return submittedOn;
    }

}
