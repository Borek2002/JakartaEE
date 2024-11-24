package pl.edu.pg.eti.kask.reservation.service.impl;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import jakarta.ws.rs.NotFoundException;
import pl.edu.pg.eti.kask.hotel.repository.api.HotelRepository;
import pl.edu.pg.eti.kask.reservation.repository.api.ReservationRepository;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.reservation.service.api.ReservationService;
import pl.edu.pg.eti.kask.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.user.repository.entity.User;
import pl.edu.pg.eti.kask.user.repository.entity.UserRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class ReservationDefaultService implements ReservationService {

    private final ReservationRepository repository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final SecurityContext securityContext;

    @Inject
    public ReservationDefaultService(ReservationRepository repository, UserRepository userRepository, HotelRepository hotelRepository, SecurityContext securityContext) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.securityContext = securityContext;
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public Optional<Reservation> getReservation(UUID uuid) {
        return this.repository.find(uuid);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Reservation> find(User user, UUID id) {
        return repository.findByIdAndUser(id, user);
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public List<Reservation> getAllReservations() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return this.repository.findAll();
        }
        User user = userRepository.findByEmail(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return this.repository.findAllByUser(user);

    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public Optional<Reservation> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return getReservation(id);
        }
        User user = userRepository.findByEmail(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(user, id);
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public void create(Reservation reservation) {
        if (hotelRepository.find(reservation.getHotel().getId()).isEmpty()) {
            throw new NotFoundException("Nie ma hotelu");
        }
        this.repository.create(reservation);
    }

    @RolesAllowed(UserRoles.USER)
    @Override
    public void createForCallerPrincipal(Reservation reservation) {
        User user = userRepository.findByEmail(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        reservation.setUser(user);
        create(reservation);
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public void update(Reservation reservation) {
        checkAdminRoleOrOwner(repository.find(reservation.getId()));
        this.repository.update(reservation);
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public void delete(Reservation reservation) {
        checkAdminRoleOrOwner(repository.find(reservation.getId()));
        this.repository.delete(reservation);
    }

    private void checkAdminRoleOrOwner(Optional<Reservation> reservation) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && reservation.isPresent()
                && reservation.get().getUser().getEmail().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}
