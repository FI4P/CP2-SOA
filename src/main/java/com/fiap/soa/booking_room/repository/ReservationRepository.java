package com.fiap.soa.booking_room.repository;

import com.fiap.soa.booking_room.domain.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.room.id = :roomId AND r.status <> 'CANCELED' AND r.checkinExpected < :checkout AND r.checkoutExpected > :checkin")
    boolean hasOverlappingReservations(@Param("roomId") UUID roomId, @Param("checkin") LocalDate checkin, @Param("checkout") LocalDate checkout);
}