package com.fiap.soa.booking_room.infrastructure.exceptions;

public class RoomUnavailableException extends RuntimeException {
    public RoomUnavailableException(String message) {
        super(message);
    }
}
