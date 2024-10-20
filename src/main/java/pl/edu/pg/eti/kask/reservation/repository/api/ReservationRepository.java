package pl.edu.pg.eti.kask.reservation.repository.api;

import pl.edu.pg.eti.kask.repository.api.Repository;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.UUID;

public interface ReservationRepository extends Repository<Reservation, UUID> {
}
