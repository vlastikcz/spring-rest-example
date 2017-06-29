package com.github.vlastikcz.springrestexample.service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.vlastikcz.springrestexample.domain.UserFeedback;
import com.github.vlastikcz.springrestexample.domain.UserFeedbackBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class InMemoryUserFeedbackServiceTest {
    private static final String NAME = "NAME";
    private static final String OTHER_NAME = "OTHER_NAME";
    private static final String UNKNOWN_NAME = "UNKNOWN_NAME";
    private static final UUID RANDOM_UUID = UUID.fromString("fe821322-dd3d-434e-8a3c-232ccbc7b2cc");
    private static final UUID OTHER_RANDOM_UUID = UUID.fromString("7f1cdc03-6344-4fdc-8d4f-4b9aaab1be1b");
    private static final UUID UNKNOWN_UUID = UUID.fromString("48b4130e-8045-4d8b-8dfd-72feebed2987");
    private static final Clock clock = Clock.fixed(Instant.MAX, ZoneId.of("GMT"));
    private static final Clock otherClock = Clock.fixed(Instant.MIN, ZoneId.of("CET"));

    private final UuidGeneratorService uuidGeneratorService = () -> RANDOM_UUID;
    private InMemoryUserFeedbackService service;

    @Before
    public void before() {
        service = new InMemoryUserFeedbackService(clock, uuidGeneratorService);
    }

    @Test
    public void createGeneratesIdAndTimestampAndReturnsTheSavedUserFeedback() throws Exception {
        final UserFeedback userFeedback = userFeedback(OTHER_RANDOM_UUID, Instant.now(otherClock), OTHER_NAME);
        final UserFeedback saved = service.save(userFeedback);
        final UserFeedback expected = userFeedback.toUserFeedbackBuilder()
                .setId(RANDOM_UUID)
                .setSubmittedOn(Instant.now(clock))
                .createUserFeedback();
        assertThat(saved).isEqualTo(expected);
    }

    @Test(expected = NullPointerException.class)
    public void createWhenNullProvidedShouldThrowNullPointerException() throws Exception {
        service.save(null);
    }

    @Test
    public void findAllWhenSomeUserFeedbackExistShouldReturnAllUserFeedback() throws Exception {
        final UserFeedback firstUserFeedback = userFeedback();
        final UserFeedback otherUserFeedback = userFeedback(OTHER_RANDOM_UUID, Instant.now(otherClock), OTHER_NAME);

        final Iterable<UserFeedback> expected = Arrays.asList(
                service.save(firstUserFeedback),
                service.save(otherUserFeedback)
        );
        final Iterable<UserFeedback> actual = service.findAll();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findAllWhenNoUserFeedbackExistShouldReturnEmptyList() throws Exception {
        final Iterable<UserFeedback> actual = service.findAll();
        assertThat(actual).isEmpty();
    }

    @Test
    public void findByNameWhenSomeMameMatchesShouldReturnOnlyMatchingUserFeedback() throws Exception {
        final UserFeedback firstUserFeedback = userFeedback();
        final UserFeedback otherUserFeedback = userFeedback(OTHER_RANDOM_UUID, Instant.now(otherClock), OTHER_NAME);

        service.save(userFeedback(OTHER_RANDOM_UUID, Instant.now(otherClock), "no match"));
        final Iterable<UserFeedback> expected = Arrays.asList(
                service.save(firstUserFeedback),
                service.save(otherUserFeedback)
        );
        final Iterable<UserFeedback> actual = service.findByName(NAME);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findByNameWhenNothingMatchesShouldReturnEmptyList() throws Exception {
        final UserFeedback firstUserFeedback = userFeedback();
        final UserFeedback otherUserFeedback = userFeedback(OTHER_RANDOM_UUID, Instant.now(otherClock), OTHER_NAME);

        service.save(firstUserFeedback);
        service.save(otherUserFeedback);
        final Iterable<UserFeedback> actual = service.findByName(UNKNOWN_NAME);
        assertThat(actual).isEmpty();
    }

    @Test(expected = NullPointerException.class)
    public void findByNameWhenNullProvidedShouldThrowNullPointerException() throws Exception {
        service.findByName(null);
    }

    @Test
    public void findByIdWhenIdExistsReturnsUserFeedback() throws Exception {
        final UuidGeneratorService uuidGeneratorServiceMock = Mockito.mock(UuidGeneratorService.class);
        final InMemoryUserFeedbackService inMemoryUserFeedbackService = new InMemoryUserFeedbackService(clock, uuidGeneratorServiceMock);
        final UserFeedback firstUserFeedback = userFeedback();
        final UserFeedback otherUserFeedback = userFeedback(OTHER_RANDOM_UUID, Instant.now(otherClock), OTHER_NAME);

        when(uuidGeneratorServiceMock.findRandomUuid()).thenReturn(RANDOM_UUID, OTHER_RANDOM_UUID);

        inMemoryUserFeedbackService.save(firstUserFeedback);
        final UserFeedback expected = inMemoryUserFeedbackService.save(otherUserFeedback);
        final Optional<UserFeedback> actual = inMemoryUserFeedbackService.findById(expected.getId());
        assertThat(actual).isEqualTo(Optional.of(expected));
    }

    @Test
    public void findByIdWhenIdDoesNotExistsReturnsEmptyOptional() throws Exception {
        final UuidGeneratorService uuidGeneratorServiceMock = Mockito.mock(UuidGeneratorService.class);
        final InMemoryUserFeedbackService inMemoryUserFeedbackService = new InMemoryUserFeedbackService(clock, uuidGeneratorServiceMock);
        final UserFeedback firstUserFeedback = userFeedback();
        final UserFeedback otherUserFeedback = userFeedback(OTHER_RANDOM_UUID, Instant.now(otherClock), OTHER_NAME);

        when(uuidGeneratorServiceMock.findRandomUuid()).thenReturn(RANDOM_UUID, OTHER_RANDOM_UUID);

        inMemoryUserFeedbackService.save(firstUserFeedback);
        inMemoryUserFeedbackService.save(otherUserFeedback);

        final Optional<UserFeedback> actual = inMemoryUserFeedbackService.findById(UNKNOWN_UUID);
        assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test(expected = NullPointerException.class)
    public void findByIdWhenNullProvidedShouldThrowNullPointerException() throws Exception {
        service.findById(null);
    }

    private static UserFeedback userFeedback() {
        return userFeedback(RANDOM_UUID, Instant.now(clock), NAME);
    }

    private static UserFeedback userFeedback(UUID uuid, Instant submittedOn, String name) {
        return new UserFeedbackBuilder()
                .setId(uuid)
                .setName(name)
                .setMessage("message 1")
                .setSubmittedOn(submittedOn)
                .createUserFeedback();
    }

}