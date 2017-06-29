package com.github.vlastikcz.springrestexample.api.v1;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.github.vlastikcz.springrestexample.api.v1.resource.ExceptionResponse;
import com.github.vlastikcz.springrestexample.api.v1.resource.MethodArgumentNotValidResponse;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerControllerAdvice {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<MethodArgumentNotValidResponse>> handleConstraintViolationException(MethodArgumentNotValidException exception) {
        final List<MethodArgumentNotValidResponse> methodArgumentNotValidResponses = exception.getBindingResult().getAllErrors().stream()
                .map(e -> createMethodArgumentNotValidResponse(e))
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(methodArgumentNotValidResponses);
    }

    private static final MethodArgumentNotValidResponse createMethodArgumentNotValidResponse(ObjectError objectError) {
        return new MethodArgumentNotValidResponse(
                objectError.getDefaultMessage(), objectError.getArguments()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(HttpServletRequest request, Exception exception) {
        logger.error("request error [url={}], [method={}]", request.getRequestURL(), request.getMethod(), exception);
        final ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getClass().getCanonicalName(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }
}
