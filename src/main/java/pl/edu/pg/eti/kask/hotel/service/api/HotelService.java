package pl.edu.pg.eti.kask.hotel.service.api;

import jakarta.ejb.Local;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Local
public interface HotelService {

    Optional<Hotel> getHotel(UUID uuid);

    List<Hotel> getHotels();

    void create(Hotel hotel);

    void update(Hotel hotel);

    void delete(Hotel hotel);
}
