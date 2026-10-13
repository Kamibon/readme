package com.readmee.readme.models;

public record ErrorResponse(
        int status,
        String message
) {}
