package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;
import com.github.vlastikcz.springrestexample.domain.UserFeedbackBuilder;

/**
 * Request object for the new {@link UserFeedback} creation
 */
public final class NewUserFeedback {
    private final String name;
    private final String message;

    @JsonCreator
    public NewUserFeedback(@JsonProperty("name") String name, @JsonProperty("message")  String message) {
        this.name = name;
        this.message = message;
    }

    public UserFeedback toUserFeedback() {
        return new UserFeedbackBuilder()
                .setName(name)
                .setMessage(message)
                .createUserFeedback();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewUserFeedback that = (NewUserFeedback) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, message);
    }

    @Override
    public String toString() {
        return "NewUserFeedback{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
