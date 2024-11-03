package pl.edu.pg.eti.kask.hotel.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.hotel.repository.api.HotelRepository;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;
import pl.edu.pg.eti.kask.reservation.repository.api.ReservationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class HotelDefaultService implements HotelService {

    private final HotelRepository repository;
    private final ReservationRepository reservationRepository;

    @Inject
    public HotelDefaultService(HotelRepository repository, ReservationRepository reservationRepository) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Optional<Hotel> getHotel(UUID uuid) {
        return this.repository.find(uuid);
    }

    @Override
    public List<Hotel> getHotels() {
        return this.repository.findAll();
    }

    @Override
    public void create(Hotel hotel) {
        this.repository.create(hotel);
    }

    @Override
    public void update(Hotel hotel) {
        this.repository.update(hotel);
    }

    @Override
    public void delete(Hotel hotel) {
        hotel.getReservations().forEach(r -> this.reservationRepository.find(r.getId())
                .ifPresent(this.reservationRepository::delete));

        this.repository.delete(hotel);
    }
}
