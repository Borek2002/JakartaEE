package pl.edu.pg.eti.kask.hotel.service.impl;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.hotel.repository.api.HotelRepository;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;
import pl.edu.pg.eti.kask.reservation.repository.api.ReservationRepository;
import pl.edu.pg.eti.kask.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.user.repository.entity.User;
import pl.edu.pg.eti.kask.user.repository.entity.UserRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@Log
public class HotelDefaultService implements HotelService {

    private final HotelRepository repository;
    private final SecurityContext securityContext;

    @Inject
    public HotelDefaultService(HotelRepository repository, SecurityContext securityContext) {
        this.repository = repository;
        this.securityContext = securityContext;
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
        this.repository.delete(hotel);
    }

}
