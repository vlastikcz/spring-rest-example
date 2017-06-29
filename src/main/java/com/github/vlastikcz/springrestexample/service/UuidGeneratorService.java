package com.github.vlastikcz.springrestexample.service;

import java.util.UUID;

/**
 * Service for generating UUIDs
 */
public interface UuidGeneratorService {
    /**
     * Find random UUID
     *
     * @return randomly generated UUID value
     */
    UUID findRandomUuid();
}
