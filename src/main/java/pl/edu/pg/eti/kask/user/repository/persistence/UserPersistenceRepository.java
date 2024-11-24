package pl.edu.pg.eti.kask.user.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class UserPersistenceRepository implements UserRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select c from User c", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        em.persist(entity);
    }

    @Override
    public void delete(User entity) {
        em.refresh(em.find(User.class, entity.getId()));
        em.remove(em.find(User.class, entity.getId()));
    }

    @Override
    public void update(User entity) {
        em.merge(entity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return Optional.of(em.createQuery("select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
