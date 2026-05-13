package com.fiap.soa.booking_room.dto;


import com.fiap.soa.booking_room.domain.enums.RoomStatus;
import com.fiap.soa.booking_room.domain.enums.RoomType;

import java.math.BigDecimal;
import java.util.UUID;

public record RoomResponseDTO(
        UUID id,
        Integer number,
        RoomType type,
        Integer capacity,
        BigDecimal pricePerNight,
        RoomStatus status
) {}