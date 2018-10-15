package com.bookingboss.productcatalog.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
public class ApiError {

    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;

    }

    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
