package com.fiap.soa.booking_room.service;

import com.fiap.soa.booking_room.domain.entities.Guest;
import com.fiap.soa.booking_room.domain.entities.Reservation;
import com.fiap.soa.booking_room.domain.entities.Room;
import com.fiap.soa.booking_room.domain.enums.ReservationStatus;
import com.fiap.soa.booking_room.dto.ReservationRequestDTO;
import com.fiap.soa.booking_room.dto.ReservationResponseDTO;
import com.fiap.soa.booking_room.infrastructure.exceptions.InvalidDateRangeException;
import com.fiap.soa.booking_room.infrastructure.exceptions.InvalidReservationStateException;
import com.fiap.soa.booking_room.infrastructure.exceptions.ResourceNotFoundException;
import com.fiap.soa.booking_room.infrastructure.exceptions.RoomUnavailableException;
import com.fiap.soa.booking_room.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
    private final GuestService guestService;
    private final RoomService roomService;

    @Transactional
    public ReservationResponseDTO create(ReservationRequestDTO dto) {
        if (!dto.checkinExpected().isBefore(dto.checkoutExpected())) {
            throw new InvalidDateRangeException("A data de check-out deve ser posterior à data de check-in.");
        }

        Room room = roomService.findEntityById(dto.roomId());

        // 2. Disponibilidade do quarto
        if (repository.hasOverlappingReservations(room.getId(), dto.checkinExpected(), dto.checkoutExpected())) {
            throw new RoomUnavailableException("O quarto não está disponível neste período.");
        }

        Guest guest = guestService.findEntityById(dto.guestId());

        // Cálculo de valor estimado
        long days = ChronoUnit.DAYS.between(dto.checkinExpected(), dto.checkoutExpected());
        BigDecimal estimated = room.getPricePerNight().multiply(BigDecimal.valueOf(days));

        Reservation reservation = Reservation.builder()
                .guest(guest)
                .room(room)
                .checkinExpected(dto.checkinExpected())
                .checkoutExpected(dto.checkoutExpected())
                .status(ReservationStatus.CREATED)
                .estimatedAmount(estimated)
                .build();

        return toResponse(repository.save(reservation));
    }

    @Transactional
    public ReservationResponseDTO checkIn(UUID id) {
        Reservation reservation = findEntityById(id);

        if (reservation.getStatus() != ReservationStatus.CREATED) {
            throw new InvalidReservationStateException("Check-in só pode ser feito em reservas com status CREATED.");
        }

        // 5. Janela de check-in (simplificada para o dia de hoje ou após)
        if (LocalDate.now().isBefore(reservation.getCheckinExpected())) {
            throw new InvalidDateRangeException("Não é possível fazer check-in antes da data prevista.");
        }

        reservation.setStatus(ReservationStatus.CHECKED_IN);
        reservation.setCheckinAt(LocalDateTime.now());

        return toResponse(repository.save(reservation));
    }

    @Transactional
    public ReservationResponseDTO checkOut(UUID id) {
        Reservation reservation = findEntityById(id);

        if (reservation.getStatus() != ReservationStatus.CHECKED_IN) {
            throw new InvalidReservationStateException("Check-out só pode ser feito em reservas com status CHECKED_IN.");
        }

        reservation.setCheckoutAt(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.CHECKED_OUT);

        // 6. Cálculo do valor efetivo
        long days = ChronoUnit.DAYS.between(reservation.getCheckinAt().toLocalDate(), reservation.getCheckoutAt().toLocalDate());
        long effectiveDays = Math.max(1, days); // Mínimo de 1 diária

        BigDecimal finalAmount = reservation.getRoom().getPricePerNight().multiply(BigDecimal.valueOf(effectiveDays));
        reservation.setFinalAmount(finalAmount);

        return toResponse(repository.save(reservation));
    }

    @Transactional
    public void cancel(UUID id) {
        Reservation reservation = findEntityById(id);

        if (reservation.getStatus() != ReservationStatus.CREATED) {
            throw new InvalidReservationStateException("Só é possível cancelar reservas que ainda não tiveram check-in (status CREATED).");
        }

        reservation.setStatus(ReservationStatus.CANCELED);
        repository.save(reservation);
    }

    private Reservation findEntityById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com ID: " + id));
    }

    private ReservationResponseDTO toResponse(Reservation res) {
        return new ReservationResponseDTO(
                res.getId(),
                guestService.toResponse(res.getGuest()),
                roomService.toResponse(res.getRoom()),
                res.getCheckinExpected(), res.getCheckoutExpected(),
                res.getCheckinAt(), res.getCheckoutAt(),
                res.getStatus(), res.getEstimatedAmount(), res.getFinalAmount()
        );
    }
}