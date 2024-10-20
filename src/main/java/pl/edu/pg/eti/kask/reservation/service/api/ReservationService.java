package pl.edu.pg.eti.kask.reservation.service.api;

import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationService {

    Optional<Reservation> getReservation(UUID uuid);

    List<Reservation> getAllReservations();

    void create(Reservation reservation);

    void update(Reservation reservation);

    void delete(Reservation reservation);
}
