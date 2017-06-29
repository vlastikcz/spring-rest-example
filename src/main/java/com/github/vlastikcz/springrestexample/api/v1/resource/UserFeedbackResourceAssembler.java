package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.vlastikcz.springrestexample.api.v1.UserFeedbackController;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;

@Component
public class UserFeedbackResourceAssembler extends ResourceAssemblerSupport<UserFeedback, UserFeedbackResource> {

    private final EntityLinks entityLinks;

    @Autowired
    public UserFeedbackResourceAssembler(EntityLinks entityLinks) {
        super(UserFeedbackController.class, UserFeedbackResource.class);
        this.entityLinks = entityLinks;
    }

    @Override
    public UserFeedbackResource toResource(UserFeedback userFeedback) {
        Objects.requireNonNull(userFeedback, "'userFeedback' cannot be null");
        final UserFeedbackResource userFeedbackResource = UserFeedbackResourceFactory.fromUserFeedback(userFeedback);
        final Link userFeedbackLink = entityLinks.linkToSingleResource(UserFeedbackResource.class, userFeedback.getId());
        userFeedbackResource.add(userFeedbackLink);
        return userFeedbackResource;
    }

}
