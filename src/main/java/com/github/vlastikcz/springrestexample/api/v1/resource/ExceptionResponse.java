package com.github.vlastikcz.springrestexample.api.v1.resource;

import java.util.Objects;

/**
 * Response object for the {@link Exception}
 */
public class ExceptionResponse {
    private final String type;
    private final String message;

    public ExceptionResponse(String type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExceptionResponse that = (ExceptionResponse) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, message);
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
