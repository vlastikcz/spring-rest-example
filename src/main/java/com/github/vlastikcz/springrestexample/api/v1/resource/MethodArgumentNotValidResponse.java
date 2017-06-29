package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Response object for the {@link MethodArgumentNotValidException}
 */
public final class MethodArgumentNotValidResponse {
    private final String message;
    private final Object[] arguments;

    public MethodArgumentNotValidResponse(String message, Object[] arguments) {
        this.message = message;
        this.arguments = arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MethodArgumentNotValidResponse that = (MethodArgumentNotValidResponse) o;
        return Objects.equals(message, that.message) &&
                Arrays.equals(arguments, that.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, arguments);
    }

    @Override
    public String toString() {
        return "MethodArgumentNotValidResponse{" +
                ", message='" + message + '\'' +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
