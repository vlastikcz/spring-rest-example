package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.time.Instant;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;

import com.github.vlastikcz.springrestexample.domain.UserFeedback;
import com.github.vlastikcz.springrestexample.domain.UserFeedbackBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UserFeedbackResourceAssemblerTest {
    private static final UUID USER_FEEDBACK_ID = UUID.fromString("6f16d5be-42b1-4b7b-b677-f2bc26a6603e");
    private static final String USER_FEEDBACK_NAME = "USER_FEEDBACK_NAME";
    private static final String USER_FEEDBACK_MESSAGE = "USER_FEEDBACK_MESSAGE";
    private static final Instant USER_FEEDBACK_SUBMITTED_ON = Instant.EPOCH;
    private static final Link USER_FEEDBACK_LINK = new Link(USER_FEEDBACK_ID.toString());

    @Mock
    private EntityLinks entityLinks;
    private UserFeedbackResourceAssembler userFeedbackResourceAssembler;

    @Before
    public void before() {
        entityLinks = Mockito.mock(EntityLinks.class);
        userFeedbackResourceAssembler = new UserFeedbackResourceAssembler(entityLinks);
    }

    @Test
    public void toResourceShouldConvertToUserFeedbackResource() throws Exception {
        final UserFeedback userFeedback = userFeedback();
        when(entityLinks.linkToSingleResource(UserFeedbackResource.class, userFeedback.getId())).thenReturn(USER_FEEDBACK_LINK);

        final UserFeedbackResource actual = userFeedbackResourceAssembler.toResource(userFeedback);
        final UserFeedbackResource expected = new UserFeedbackResource(USER_FEEDBACK_NAME, USER_FEEDBACK_MESSAGE, USER_FEEDBACK_SUBMITTED_ON);
        expected.add(USER_FEEDBACK_LINK);

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = NullPointerException.class)
    public void toResourceWhenNullProvidedShouldThrowNullPointerException() throws Exception {
        userFeedbackResourceAssembler.toResource(null);
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