package com.fiap.soa.booking_room.repository;

import com.fiap.soa.booking_room.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}