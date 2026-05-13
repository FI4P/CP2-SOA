package com.fiap.soa.booking_room.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record ReservationRequestDTO(
        @NotNull(message = "O ID do hóspede é obrigatório")
        UUID guestId,

        @NotNull(message = "O ID do quarto é obrigatório")
        UUID roomId,

        @NotNull(message = "A data prevista de check-in é obrigatória")
        @FutureOrPresent(message = "A data de check-in deve ser hoje ou no futuro")
        LocalDate checkinExpected,

        @NotNull(message = "A data prevista de check-out é obrigatória")
        @Future(message = "A data de check-out deve ser uma data futura")
        LocalDate checkoutExpected
) {}