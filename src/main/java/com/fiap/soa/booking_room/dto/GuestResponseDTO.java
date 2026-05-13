package com.fiap.soa.booking_room.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record GuestResponseDTO(
        UUID id,
        String fullName,
        String document,
        String email,
        String phone,
        LocalDateTime createdAt
) {}