package com.fiap.soa.booking_room.infrastructure.exceptions;

public class InvalidReservationStateException extends RuntimeException {
    public InvalidReservationStateException(String message) {
        super(message);
    }
}
