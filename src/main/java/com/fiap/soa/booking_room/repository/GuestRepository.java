package com.fiap.soa.booking_room.repository;
import com.fiap.soa.booking_room.domain.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GuestRepository extends JpaRepository<Guest, UUID> {
    boolean existsByDocumentOrEmail(String document, String email);
}