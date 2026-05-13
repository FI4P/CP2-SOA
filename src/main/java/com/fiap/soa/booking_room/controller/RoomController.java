package com.fiap.soa.booking_room.controller;

import com.fiap.soa.booking_room.dto.RoomRequestDTO;
import com.fiap.soa.booking_room.dto.RoomResponseDTO;
import com.fiap.soa.booking_room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@Valid @RequestBody RoomRequestDTO dto) {
        RoomResponseDTO response = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // Usando DELETE semanticamente, mas no Service ele apenas inativa o quarto (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        service.deactivateRoom(id);
        return ResponseEntity.noContent().build();
    }
}