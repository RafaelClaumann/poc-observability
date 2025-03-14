package com.observabilitypoc.cache.except;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(String message, HttpStatus status, LocalDateTime timestamp) {
}
