package com.github.vlastikcz.springrestexample.api.v1.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;
import com.github.vlastikcz.springrestexample.domain.UserFeedbackBuilder;

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
}
