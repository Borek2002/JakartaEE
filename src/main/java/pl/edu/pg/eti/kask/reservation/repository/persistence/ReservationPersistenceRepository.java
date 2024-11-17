package pl.edu.pg.eti.kask.reservation.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.reservation.repository.api.ReservationRepository;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ReservationPersistenceRepository implements ReservationRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Reservation> find(UUID id) {
        return Optional.ofNullable(em.find(Reservation.class, id));
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("select c from Reservation c", Reservation.class).getResultList();
    }

    @Override
    public void create(Reservation entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Reservation entity) {
        em.refresh(em.find(Reservation.class, entity.getId()));
        em.remove(em.find(Reservation.class, entity.getId()));
    }

    @Override
    public void update(Reservation entity) {
        em.merge(entity);
    }
}
