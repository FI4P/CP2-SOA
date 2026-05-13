package com.fiap.soa.booking_room.dto;
import java.time.LocalDateTime;

public record StandardError(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {}
