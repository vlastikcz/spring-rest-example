package com.github.vlastikcz.springrestexample.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * Default implementation of the {@link UuidGeneratorService}
 */
@Service
public class DefaultUuidGeneratorService implements UuidGeneratorService {

    @Override
    public UUID findRandomUuid() {
        return UUID.randomUUID();
    }
}
