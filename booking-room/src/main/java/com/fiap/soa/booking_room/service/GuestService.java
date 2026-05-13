package com.fiap.soa.booking_room.service;

import com.fiap.soa.booking_room.domain.entities.Guest;
import com.fiap.soa.booking_room.dto.GuestRequestDTO;
import com.fiap.soa.booking_room.dto.GuestResponseDTO;
import com.fiap.soa.booking_room.infrastructure.exceptions.ResourceNotFoundException;
import com.fiap.soa.booking_room.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository repository;

    @Transactional
    public GuestResponseDTO create(GuestRequestDTO dto) {
        if (repository.existsByDocumentOrEmail(dto.document(), dto.email())) {
            throw new IllegalArgumentException("Hóspede com este documento ou e-mail já cadastrado.");
        }

        Guest guest = Guest.builder()
                .fullName(dto.fullName())
                .document(dto.document())
                .email(dto.email())
                .phone(dto.phone())
                .build();

        guest = repository.save(guest);
        return toResponse(guest);
    }

    public Guest findEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hóspede não encontrado com ID: " + id));
    }

    public GuestResponseDTO findById(UUID id) {
        return toResponse(findEntityById(id));
    }

    public GuestResponseDTO toResponse(Guest guest) {
        return new GuestResponseDTO(guest.getId(), guest.getFullName(), guest.getDocument(), guest.getEmail(), guest.getPhone(), guest.getCreatedAt());
    }
}