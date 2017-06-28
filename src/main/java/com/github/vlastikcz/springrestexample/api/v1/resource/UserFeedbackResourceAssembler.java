package com.github.vlastikcz.springrestexample.api.v1.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.github.vlastikcz.springrestexample.api.v1.UserFeedbackController;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;

@Component
public class UserFeedbackResourceAssembler extends ResourceAssemblerSupport<UserFeedback, UserFeedbackResource> {
    /**
     * Creates a new {@link ResourceAssemblerSupport} using the given controller class and resource type.
     */
    public UserFeedbackResourceAssembler() {
        super(UserFeedbackController.class, UserFeedbackResource.class);
    }

    @Override
    public UserFeedbackResource toResource(UserFeedback entity) {
        return UserFeedbackResourceFactory.fromUserFeedback(entity);
    }

}
