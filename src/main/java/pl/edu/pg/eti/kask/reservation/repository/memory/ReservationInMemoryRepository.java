package pl.edu.pg.eti.kask.reservation.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.datastore.component.DataStore;
import pl.edu.pg.eti.kask.reservation.repository.api.ReservationRepository;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ReservationInMemoryRepository implements ReservationRepository {

    private final DataStore store;

    @Inject
    public ReservationInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Reservation> find(UUID id) {
        return store.findAllReservations().stream()
                .filter(reservation -> reservation.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Reservation> findAll() {
        return store.findAllReservations();
    }

    @Override
    public void create(Reservation entity) {
        this.store.createReservation(entity);
    }

    @Override
    public void delete(Reservation entity) {
        this.store.deleteReservation(entity.getId());
    }

    @Override
    public void update(Reservation entity) {
        this.store.updateReservation(entity);
    }
}
