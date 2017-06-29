package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.time.Instant;
import java.util.Objects;

import org.springframework.hateoas.ResourceSupport;

import com.github.vlastikcz.springrestexample.domain.UserFeedback;

/**
 * HATEOAS resource representation of {@link UserFeedback}
 */
public final class UserFeedbackResource extends ResourceSupport {

    private final String name;
    private final String message;
    private final Instant submittedOn;

    public UserFeedbackResource(String name, String message, Instant submittedOn) {
        this.name = name;
        this.message = message;
        this.submittedOn = submittedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        UserFeedbackResource that = (UserFeedbackResource) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(message, that.message) &&
                Objects.equals(submittedOn, that.submittedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, message, submittedOn);
    }

    @Override
    public String toString() {
        return "UserFeedbackResource{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", submittedOn=" + submittedOn +
                "} " + super.toString();
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
