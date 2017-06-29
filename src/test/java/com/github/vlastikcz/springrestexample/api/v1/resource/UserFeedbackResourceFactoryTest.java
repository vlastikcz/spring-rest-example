package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.time.Instant;
import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.hateoas.Link;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.vlastikcz.springrestexample.api.v1.UserFeedbackController;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;
import com.github.vlastikcz.springrestexample.domain.UserFeedbackBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFeedbackResourceFactoryTest {
    private final static UUID USER_FEEDBACK_ID = UUID.fromString("6f16d5be-42b1-4b7b-b677-f2bc26a6603e");
    private final static String USER_FEEDBACK_NAME = "USER_FEEDBACK_NAME";
    private final static String USER_FEEDBACK_MESSAGE = "USER_FEEDBACK_MESSAGE";
    private final static Instant USER_FEEDBACK_SUBMITTED_ON = Instant.EPOCH;

    @Test
    public void fromUserFeedbackShouldConvertToUserFeedbackResource() throws Exception {
        final UserFeedback userFeedback = userFeedback();
        final UserFeedbackResource actual = UserFeedbackResourceFactory.fromUserFeedback(userFeedback);
        final UserFeedbackResource expected = new UserFeedbackResource(USER_FEEDBACK_NAME, USER_FEEDBACK_MESSAGE, USER_FEEDBACK_SUBMITTED_ON);
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = NullPointerException.class)
    public void fromUserFeedbackWhenNullProvidedShouldThrowNullPointerException() throws Exception {
        UserFeedbackResourceFactory.fromUserFeedback(null);
    }

    private static UserFeedback userFeedback() {
        return new UserFeedbackBuilder()
                .setId(USER_FEEDBACK_ID)
                .setName(USER_FEEDBACK_NAME)
                .setMessage(USER_FEEDBACK_MESSAGE)
                .setSubmittedOn(USER_FEEDBACK_SUBMITTED_ON)
                .createUserFeedback();
    }

}