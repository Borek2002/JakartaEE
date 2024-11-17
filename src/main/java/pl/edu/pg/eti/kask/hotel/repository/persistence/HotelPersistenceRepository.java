package pl.edu.pg.eti.kask.hotel.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.hotel.repository.api.HotelRepository;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class HotelPersistenceRepository implements HotelRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Hotel> find(UUID id) {
        return Optional.ofNullable(em.find(Hotel.class, id));
    }

    @Override
    public List<Hotel> findAll() {
        return em.createQuery("select c from Hotel c", Hotel.class).getResultList();
    }

    @Override
    public void create(Hotel entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Hotel entity) {
        em.refresh(em.find(Hotel.class, entity.getId()));
        em.remove(em.find(Hotel.class, entity.getId()));
    }

    @Override
    public void update(Hotel entity) {
        em.merge(entity);
    }
}
