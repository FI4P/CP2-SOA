package com.fiap.soa.booking_room.infrastructure.exceptions;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException(String message) {
        super(message);
    }
}