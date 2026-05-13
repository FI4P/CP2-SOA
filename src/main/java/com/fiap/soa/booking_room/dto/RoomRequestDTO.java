package com.fiap.soa.booking_room.dto;

import com.fiap.soa.booking_room.domain.enums.RoomStatus;
import com.fiap.soa.booking_room.domain.enums.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record RoomRequestDTO(
        @NotNull(message = "O número do quarto é obrigatório")
        Integer number,

        @NotNull(message = "O tipo do quarto é obrigatório")
        RoomType type,

        @NotNull(message = "A capacidade é obrigatória")
        @Min(value = 1, message = "A capacidade mínima é 1 hóspede")
        Integer capacity,

        @NotNull(message = "O preço da diária é obrigatório")
        @Positive(message = "O preço base deve ser maior que zero")
        BigDecimal pricePerNight,

        @NotNull(message = "O status do quarto é obrigatório")
        RoomStatus status
) {}