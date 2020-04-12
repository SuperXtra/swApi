package com.softwareplant.sw.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class ErrorDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    private ErrorDetails() {
        timestamp = LocalDateTime.now();
    }

    public ErrorDetails(HttpStatus status) {
        this();
        this.status = status;
    }

    public ErrorDetails(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
    }

    public ErrorDetails(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
    }


}
