package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.util.Objects;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.vlastikcz.springrestexample.api.v1.UserFeedbackController;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserFeedbackResourceAssembler extends ResourceAssemblerSupport<UserFeedback, UserFeedbackResource> {
    /**
     * Creates a new {@link ResourceAssemblerSupport} using the given controller class and resource type.
     */
    public UserFeedbackResourceAssembler() {
        super(UserFeedbackController.class, UserFeedbackResource.class);
    }

    @Override
    public UserFeedbackResource toResource(UserFeedback userFeedback) {
        Objects.requireNonNull(userFeedback, "'userFeedback' cannot be null");
        final UserFeedbackResource userFeedbackResource = UserFeedbackResourceFactory.fromUserFeedback(userFeedback);
        final Link userFeedbackLink = linkTo(methodOn(UserFeedbackController.class)
                .findFeedbackById(userFeedback.getId()))
                .withSelfRel();
        userFeedbackResource.add(userFeedbackLink);
        return userFeedbackResource;
    }

}
