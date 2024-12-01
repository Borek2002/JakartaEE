package pl.edu.pg.eti.kask.hotel.repository.api;

import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.repository.api.Repository;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface HotelRepository extends Repository<Hotel, UUID> {

}
