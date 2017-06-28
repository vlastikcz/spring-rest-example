package com.github.vlastikcz.springrestexample.domain;

import java.time.Instant;
import java.util.UUID;

public class UserFeedbackBuilder {
    private UUID id;
    private String name;
    private String message;
    private Instant submittedOn;

    public UserFeedbackBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public UserFeedbackBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserFeedbackBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public UserFeedbackBuilder setSubmittedOn(Instant submittedOn) {
        this.submittedOn = submittedOn;
        return this;
    }

    public UserFeedback createUserFeedback() {
        return new UserFeedback(id, name, message, submittedOn);
    }
}