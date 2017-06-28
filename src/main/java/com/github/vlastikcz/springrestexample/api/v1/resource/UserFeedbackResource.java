package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.time.Instant;

import org.springframework.hateoas.ResourceSupport;

public final class UserFeedbackResource extends ResourceSupport {
    private final String name;
    private final String message;
    private final Instant submittedOn;

    public UserFeedbackResource(String name, String message, Instant submittedOn) {
        this.name = name;
        this.message = message;
        this.submittedOn = submittedOn;
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
