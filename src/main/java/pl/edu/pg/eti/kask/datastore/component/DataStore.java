package pl.edu.pg.eti.kask.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.serialization.component.CloningUtility;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<Reservation> reservations = new HashSet<>();
    private final Set<Hotel> hotels = new HashSet<>();
    private final Set<User> users = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Hotel> findAllHotels() {
        return hotels.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Reservation> findAllReservations() {
        return reservations.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(character -> character.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    public synchronized void createHotel(Hotel value) throws IllegalArgumentException {
        if (hotels.stream().anyMatch(hotel -> hotel.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The hotel id \"%s\" is not unique".formatted(value.getId()));
        }
        hotels.add(cloningUtility.clone(value));
    }

    public synchronized void createReservation(Reservation value) throws IllegalArgumentException {
        if (reservations.stream().anyMatch(reservation -> reservation.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The reservation id \"%s\" is not unique".formatted(value.getId()));
        }
        reservations.add(cloningUtility.clone(value));
    }

    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(character -> character.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void updateHotel(Hotel value) throws IllegalArgumentException {
        if (hotels.removeIf(hotel -> hotel.getId().equals(value.getId()))) {
            hotels.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The hotel with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void updateReservation(Reservation value) throws IllegalArgumentException {
        if (reservations.removeIf(reservation -> reservation.getId().equals(value.getId()))) {
            reservations.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The reservation with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteUser(UUID id) throws IllegalArgumentException {
        if (!users.removeIf(user -> user.getId().equals(id))) {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized void deleteHotel(UUID id) throws IllegalArgumentException {
        if (!hotels.removeIf(hotel -> hotel.getId().equals(id))) {
            throw new IllegalArgumentException("The hotel with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized void deleteReservation(UUID id) throws IllegalArgumentException {
        if (!reservations.removeIf(reservation -> reservation.getId().equals(id))) {
            throw new IllegalArgumentException("The reservation with id \"%s\" does not exist".formatted(id));
        }
    }

}
