package com.fiap.soa.booking_room.service;

import com.fiap.soa.booking_room.domain.entities.Room;
import com.fiap.soa.booking_room.domain.enums.RoomStatus;
import com.fiap.soa.booking_room.dto.RoomRequestDTO;
import com.fiap.soa.booking_room.dto.RoomResponseDTO;
import com.fiap.soa.booking_room.infrastructure.exceptions.ResourceNotFoundException;
import com.fiap.soa.booking_room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;

    @Transactional
    public RoomResponseDTO create(RoomRequestDTO dto) {
        Room room = Room.builder()
                .number(dto.number())
                .type(dto.type())
                .capacity(dto.capacity())
                .pricePerNight(dto.pricePerNight())
                .status(dto.status())
                .build();

        room = repository.save(room);
        return toResponse(room);
    }

    public Room findEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado com ID: " + id));
    }

    public RoomResponseDTO findById(UUID id) {
        return toResponse(findEntityById(id));
    }

    @Transactional
    public void deactivateRoom(UUID id) {
        Room room = findEntityById(id);
        room.setStatus(RoomStatus.INATIVO);
        repository.save(room);
    }

    public RoomResponseDTO toResponse(Room room) {
        return new RoomResponseDTO(room.getId(), room.getNumber(), room.getType(), room.getCapacity(), room.getPricePerNight(), room.getStatus());
    }
}