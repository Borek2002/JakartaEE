package pl.edu.pg.eti.kask.reservation.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.repository.api.ReservationRepository;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);

        cq.select(root);

        return em.createQuery(cq).getResultList();
    }

    @Override
    public void create(Reservation entity) {
        em.persist(entity);
        em.refresh(em.find(Hotel.class, entity.getHotel().getId()));
    }

    @Override
    public void delete(Reservation entity) {
        em.refresh(em.find(Reservation.class, entity.getId()));
        em.remove(em.find(Reservation.class, entity.getId()));
        em.refresh(em.find(Hotel.class, entity.getHotel().getId()));
    }

    @Override
    public void update(Reservation entity) {
        if (!em.isJoinedToTransaction()) {
            em.joinTransaction();
        }
        em.merge(entity);
    }

    @Override
    public Optional<Reservation> findByIdAndUser(UUID id, User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);
        Predicate idPredicate = cb.equal(root.get("id"), id);
        Predicate userPredicate = cb.equal(root.get("user"), user);

        cq.where(cb.and(idPredicate, userPredicate));

        try {
            Reservation result = em.createQuery(cq).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Reservation> findAllByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> root = cq.from(Reservation.class);

        Predicate userPredicate = cb.equal(root.get("user"), user);

        cq.where(userPredicate);

        return em.createQuery(cq).getResultList();
    }
}
