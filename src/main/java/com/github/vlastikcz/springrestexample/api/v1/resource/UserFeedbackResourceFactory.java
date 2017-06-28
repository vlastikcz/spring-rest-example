package com.github.vlastikcz.springrestexample.api.v1.resource;

import org.springframework.hateoas.Link;

import com.github.vlastikcz.springrestexample.api.v1.UserFeedbackController;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserFeedbackResourceFactory {
    static UserFeedbackResource fromUserFeedback(UserFeedback userFeedback) {
        final UserFeedbackResource userFeedbackResource = new UserFeedbackResource(
                userFeedback.getName(), userFeedback.getMessage(), userFeedback.getSubmittedOn()
        );
        final Link userFeedbackLink = linkTo(methodOn(UserFeedbackController.class).showFeedbackById(userFeedback.getId())).withRel("self");
        userFeedbackResource.add(userFeedbackLink);
        return userFeedbackResource;
    }
}
