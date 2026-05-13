package com.fiap.soa.booking_room.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record GuestRequestDTO(
        @NotBlank(message = "O nome completo é obrigatório")
        String fullName,

        @NotBlank(message = "O documento (CPF/Passaporte) é obrigatório")
        String document,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O formato do e-mail é inválido")
        String email,

        String phone
) {}