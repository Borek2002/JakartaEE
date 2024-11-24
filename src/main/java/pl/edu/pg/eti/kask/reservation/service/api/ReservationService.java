package pl.edu.pg.eti.kask.reservation.service.api;

import jakarta.ejb.Local;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Local
public interface ReservationService {

    Optional<Reservation> getReservation(UUID uuid);

    List<Reservation> getAllReservations();

    void create(Reservation reservation);

    void update(Reservation reservation);

    void delete(Reservation reservation);
    void createForCallerPrincipal(Reservation reservation);
    Optional<Reservation> findForCallerPrincipal(UUID id);

    Optional<Reservation> find(User user, UUID id);
}
