package pl.edu.pg.eti.kask.reservation.repository.api;

import pl.edu.pg.eti.kask.repository.api.Repository;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends Repository<Reservation, UUID> {

    Optional<Reservation> findByIdAndUser(UUID id, User user);

    /**
     * Seeks for all user's characters.
     *
     * @param user characters' owner
     * @return list (can be empty) of user's characters
     */
    List<Reservation> findAllByUser(User user);
}
