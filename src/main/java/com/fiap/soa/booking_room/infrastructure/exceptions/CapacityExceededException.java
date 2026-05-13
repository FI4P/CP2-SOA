package com.fiap.soa.booking_room.infrastructure.exceptions;

public class CapacityExceededException extends RuntimeException {
    public CapacityExceededException(String message) {
        super(message);
    }
}
