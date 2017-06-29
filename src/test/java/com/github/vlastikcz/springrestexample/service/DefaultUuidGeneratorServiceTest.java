package com.github.vlastikcz.springrestexample.service;

import java.util.UUID;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultUuidGeneratorServiceTest {

    @Test
    public void findRandomUUUIDShouldNotReturnNull() throws Exception {
        final DefaultUuidGeneratorService defaultUuidGeneratorService = new DefaultUuidGeneratorService();
        final UUID uuid = defaultUuidGeneratorService.findRandomUuid();
        assertThat(uuid).isNotNull();
    }

    @Test
    public void findRandomUUUIDShouldReturnUUIDVersion4() throws Exception {
        final DefaultUuidGeneratorService defaultUuidGeneratorService = new DefaultUuidGeneratorService();
        final UUID uuid = defaultUuidGeneratorService.findRandomUuid();
        assertThat(uuid.version()).isEqualTo(4);
    }

    @Test
    public void findRandomUUUIDShouldNotReturnTheSameValues() throws Exception {
        final DefaultUuidGeneratorService defaultUuidGeneratorService = new DefaultUuidGeneratorService();
        final UUID uuid1 = defaultUuidGeneratorService.findRandomUuid();
        final UUID uuid2 = defaultUuidGeneratorService.findRandomUuid();
        assertThat(uuid1).isNotEqualTo(uuid2);
    }

}