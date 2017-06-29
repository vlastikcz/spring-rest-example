package com.github.vlastikcz.springrestexample.api.v1;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vlastikcz.springrestexample.SpringRestExampleApplication;
import com.github.vlastikcz.springrestexample.api.v1.resource.NewUserFeedback;
import com.github.vlastikcz.springrestexample.api.v1.resource.UserFeedbackResource;
import com.github.vlastikcz.springrestexample.api.v1.resource.UserFeedbackResourceAssembler;
import com.github.vlastikcz.springrestexample.domain.UserFeedback;
import com.github.vlastikcz.springrestexample.domain.UserFeedbackBuilder;
import com.github.vlastikcz.springrestexample.service.UserFeedbackService;

import static com.github.vlastikcz.springrestexample.api.v1.UserFeedbackController.USER_FEEDBACK_CONTROLLER_PATH;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserFeedbackController.class)
@ContextConfiguration(classes = SpringRestExampleApplication.class)
public class UserFeedbackControllerTest {

    private static final String USER_FEEDBACK_KEYWORD = "_USER_FEEDBACK";

    private static final UserFeedback SAMPLE_USER_FEEDBACK = new UserFeedbackBuilder().setId(UUID.fromString("18faa90b-aa4f-4e21-b653-9908dd61c51c"))
            .setName("SAMPLE" + USER_FEEDBACK_KEYWORD)
            .setMessage("sample message")
            .setSubmittedOn(Instant.parse("2007-12-03T10:15:30.00Z"))
            .createUserFeedback();

    private static final UserFeedback ANOTHER_SAMPLE_USER_FEEDBACK = new UserFeedbackBuilder().setId(UUID.fromString
            ("1316bb3d-779f-491b-b606-a422a427115e"))
            .setName("ANOTHER_SAMPLE" + USER_FEEDBACK_KEYWORD)
            .setMessage("another message")
            .setSubmittedOn(Instant.parse("2017-12-03T10:15:30.00Z"))
            .createUserFeedback();

    private static final List<UserFeedback> USER_FEEDBACK_LIST = Arrays.asList(SAMPLE_USER_FEEDBACK, ANOTHER_SAMPLE_USER_FEEDBACK);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFeedbackService userFeedbackService;

    @MockBean
    private UserFeedbackResourceAssembler userFeedbackResourceAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getUserFeedbackWhenNoItemsFoundShouldReturnEmptyArray() throws Exception {
        when(userFeedbackService.findAll()).thenReturn(emptyList());
        mockMvc.perform(get(USER_FEEDBACK_CONTROLLER_PATH).accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(new JSONArray().toString()));
    }

    @Test
    public void getUserFeedbackWhenSomeItemsFoundShouldReturnUserFeedbackResources() throws Exception {
        final List<UserFeedbackResource> userFeedbackResources = USER_FEEDBACK_LIST.stream().map(f -> convertToResourceFake(f)).collect(toList());
        when(userFeedbackService.findAll()).thenReturn(USER_FEEDBACK_LIST);
        when(userFeedbackResourceAssembler.toResources(USER_FEEDBACK_LIST)).thenReturn(userFeedbackResources);
        mockMvc.perform(get(USER_FEEDBACK_CONTROLLER_PATH).accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userFeedbackResources)));
    }

    @Test
    public void getUserFeedbackByNameWhenSomeItemsFoundShouldReturnMatchingUserFeedbackResources() throws Exception {
        final List<UserFeedbackResource> userFeedbackResources = USER_FEEDBACK_LIST.stream().map(f -> convertToResourceFake(f)).collect(toList());
        when(userFeedbackService.findByName(USER_FEEDBACK_KEYWORD)).thenReturn(USER_FEEDBACK_LIST);
        when(userFeedbackResourceAssembler.toResources(USER_FEEDBACK_LIST)).thenReturn(userFeedbackResources);
        final RequestBuilder requestBuilder = get(USER_FEEDBACK_CONTROLLER_PATH)
                .param(UserFeedbackController.LIST_FEEDBACK_BY_NAME_PARAM, USER_FEEDBACK_KEYWORD)
                .accept(MediaTypes.HAL_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userFeedbackResources)));
    }

    @Test
    public void getUserFeedbackByNameWhenNoItemsFoundShouldReturnEmptyArray() throws Exception {
        final RequestBuilder requestBuilder = get(USER_FEEDBACK_CONTROLLER_PATH)
                .param(UserFeedbackController.LIST_FEEDBACK_BY_NAME_PARAM, USER_FEEDBACK_KEYWORD)
                .accept(MediaTypes.HAL_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(new JSONArray().toString()));
    }

    @Test
    public void postUserFeedbackWhenAllFieldsAreFilledShouldReturnSavedUserFeedback() throws Exception {
        final NewUserFeedback newUserFeedback = new NewUserFeedback("name", "message");
        final UserFeedbackResource userFeedbackResource = convertToResourceFake(SAMPLE_USER_FEEDBACK);
        when(userFeedbackService.save(newUserFeedback.toUserFeedback())).thenReturn(SAMPLE_USER_FEEDBACK);
        when(userFeedbackResourceAssembler.toResource(SAMPLE_USER_FEEDBACK)).thenReturn(userFeedbackResource);
        final String request = objectMapper.writeValueAsString(newUserFeedback);

        mockMvc.perform(post(USER_FEEDBACK_CONTROLLER_PATH).contentType(MediaTypes.HAL_JSON).accept(MediaTypes.HAL_JSON).content(request))
                .andExpect(status().isCreated())
                .andExpect(header().string(HttpHeaders.LOCATION, userFeedbackResource.getLink(Link.REL_SELF).getHref()))
                .andExpect(content().json(objectMapper.writeValueAsString(userFeedbackResource)));
    }

    @Test
    public void postUserFeedbackWhenNameIsEmptyShouldReturnBadRequest() throws Exception {
        final NewUserFeedback newUserFeedback = new NewUserFeedback("", "message");
        final String request = objectMapper.writeValueAsString(newUserFeedback);

        mockMvc.perform(post(USER_FEEDBACK_CONTROLLER_PATH).contentType(MediaTypes.HAL_JSON).accept(MediaTypes.HAL_JSON).content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUserFeedbackWhenNameIsNullShouldReturnBadRequest() throws Exception {
        final NewUserFeedback newUserFeedback = new NewUserFeedback(null, "message");
        final String request = objectMapper.writeValueAsString(newUserFeedback);

        mockMvc.perform(post(USER_FEEDBACK_CONTROLLER_PATH).contentType(MediaTypes.HAL_JSON).accept(MediaTypes.HAL_JSON).content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUserFeedbackWhenNameIsTooLongShouldReturnBadRequest() throws Exception {
        final int length = 513;
        final NewUserFeedback newUserFeedback = new NewUserFeedback(String.join("", Collections.nCopies(length, "x")), "message");
        final String request = objectMapper.writeValueAsString(newUserFeedback);

        mockMvc.perform(post(USER_FEEDBACK_CONTROLLER_PATH).contentType(MediaTypes.HAL_JSON).accept(MediaTypes.HAL_JSON).content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUserFeedbackWhenMessageIsEmptyShouldReturnBadRequest() throws Exception {
        final NewUserFeedback newUserFeedback = new NewUserFeedback("name", "");
        final String request = objectMapper.writeValueAsString(newUserFeedback);

        mockMvc.perform(post(USER_FEEDBACK_CONTROLLER_PATH).contentType(MediaTypes.HAL_JSON).accept(MediaTypes.HAL_JSON).content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUserFeedbackWhenMessageIsNullShouldReturnBadRequest() throws Exception {
        final NewUserFeedback newUserFeedback = new NewUserFeedback("name", null);
        final String request = objectMapper.writeValueAsString(newUserFeedback);

        mockMvc.perform(post(USER_FEEDBACK_CONTROLLER_PATH).contentType(MediaTypes.HAL_JSON).accept(MediaTypes.HAL_JSON).content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postUserFeedbackWhenMessageIsTooLongShouldReturnBadRequest() throws Exception {
        final int length = 1025;
        final NewUserFeedback newUserFeedback = new NewUserFeedback(String.join("", Collections.nCopies(length, "y")), "message");
        final String request = objectMapper.writeValueAsString(newUserFeedback);

        mockMvc.perform(post(USER_FEEDBACK_CONTROLLER_PATH).contentType(MediaTypes.HAL_JSON).accept(MediaTypes.HAL_JSON).content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserFeedbackByIdWhenUnknownIdProvidedShouldReturnNotFound() throws Exception {
        final UUID id = UUID.fromString("5687c0f1-8c9c-4f30-888f-cff5da1cf936");
        when(userFeedbackService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get(USER_FEEDBACK_CONTROLLER_PATH + "/{id}", id).accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUserFeedbackByIdWhenExistingIdProvidedShouldReturnFeedback() throws Exception {
        final UserFeedbackResource userFeedbackResource = convertToResourceFake(SAMPLE_USER_FEEDBACK);
        when(userFeedbackService.findById(SAMPLE_USER_FEEDBACK.getId())).thenReturn(Optional.of(SAMPLE_USER_FEEDBACK));
        when(userFeedbackResourceAssembler.toResource(SAMPLE_USER_FEEDBACK)).thenReturn(userFeedbackResource);
        final String expectedJson = objectMapper.writeValueAsString(userFeedbackResource);
        mockMvc.perform(get(USER_FEEDBACK_CONTROLLER_PATH + "/{id}", SAMPLE_USER_FEEDBACK.getId()).accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    private static UserFeedbackResource convertToResourceFake(UserFeedback userFeedback) {
        final UserFeedbackResource userFeedbackResource = new UserFeedbackResource(
                userFeedback.getName(), userFeedback.getMessage(), userFeedback.getSubmittedOn()
        );
        userFeedbackResource.add(new Link(userFeedback.getId().toString(), Link.REL_SELF));
        return userFeedbackResource;
    }
}