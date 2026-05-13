package com.fiap.soa.booking_room.controller;



import com.fiap.soa.booking_room.dto.ReservationRequestDTO;
import com.fiap.soa.booking_room.dto.ReservationResponseDTO;
import com.fiap.soa.booking_room.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody ReservationRequestDTO dto) {
        ReservationResponseDTO response = service.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{id}/checkin")
    public ResponseEntity<ReservationResponseDTO> checkIn(@PathVariable UUID id) {
        return ResponseEntity.ok(service.checkIn(id));
    }

    @PatchMapping("/{id}/checkout")
    public ResponseEntity<ReservationResponseDTO> checkOut(@PathVariable UUID id) {
        return ResponseEntity.ok(service.checkOut(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        service.cancel(id);
        return ResponseEntity.noContent().build();
    }
}