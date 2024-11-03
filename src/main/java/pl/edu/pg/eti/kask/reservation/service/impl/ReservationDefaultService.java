package pl.edu.pg.eti.kask.reservation.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import jakarta.ws.rs.NotFoundException;
import pl.edu.pg.eti.kask.hotel.repository.api.HotelRepository;
import pl.edu.pg.eti.kask.reservation.repository.api.ReservationRepository;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.reservation.service.api.ReservationService;
import pl.edu.pg.eti.kask.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class ReservationDefaultService implements ReservationService {

    private final ReservationRepository repository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    @Inject
    public ReservationDefaultService(ReservationRepository repository, UserRepository userRepository, HotelRepository hotelRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Optional<Reservation> getReservation(UUID uuid) {
        return this.repository.find(uuid);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return this.repository.findAll();
    }

    @Override
    public void create(Reservation reservation) {
        this.hotelRepository.find(reservation.getHotel().getId())
                .ifPresentOrElse(h ->{
                    h.getReservations().add(reservation);
                    this.hotelRepository.update(h);
                }, ()->{throw new NotFoundException();});
        if(reservation.getUser()!=null) {
            this.userRepository.find(reservation.getUser().getId())
                    .ifPresentOrElse(h -> {
                        h.getReservations().add(reservation);
                        this.userRepository.update(h);
                    }, () -> {
                        throw new NotFoundException();
                    });
        }
        this.repository.create(reservation);
    }

    @Override
    public void update(Reservation reservation) {
        this.repository.update(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        this.hotelRepository.find(reservation.getHotel().getId())
                .ifPresentOrElse(h ->{
                    h.getReservations().remove(reservation);
                    this.hotelRepository.update(h);
                }, ()->{throw new NotFoundException();});
        if(reservation.getUser()!=null) {
            this.userRepository.find(reservation.getUser().getId())
                    .ifPresentOrElse(h -> {
                        h.getReservations().remove(reservation);
                        this.userRepository.update(h);
                    }, () -> {
                        throw new NotFoundException();
                    });
        }
        this.repository.delete(reservation);
    }
}
