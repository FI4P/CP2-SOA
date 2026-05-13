package com.fiap.soa.booking_room.dto;

import com.fiap.soa.booking_room.domain.enums.ReservationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponseDTO(
        UUID id,
        GuestResponseDTO guest,
        RoomResponseDTO room,
        LocalDate checkinExpected,
        LocalDate checkoutExpected,
        LocalDateTime checkinAt,
        LocalDateTime checkoutAt,
        ReservationStatus status,
        BigDecimal estimatedAmount,
        BigDecimal finalAmount
) {}