package com.fiap.soa.booking_room.dto;


import java.time.LocalDateTime;
import java.util.List;

public record ValidationError(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path,
        List<FieldMessage> errors
) {
    public record FieldMessage(String fieldName, String message) {}
}