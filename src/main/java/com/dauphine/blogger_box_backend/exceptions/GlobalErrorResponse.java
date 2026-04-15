package com.dauphine.blogger_box_backend.exceptions;


import java.time.LocalDateTime;

public record GlobalErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {}