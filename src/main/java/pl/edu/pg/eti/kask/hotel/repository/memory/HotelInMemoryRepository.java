package pl.edu.pg.eti.kask.hotel.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.datastore.component.DataStore;
import pl.edu.pg.eti.kask.hotel.repository.api.HotelRepository;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class HotelInMemoryRepository implements HotelRepository {

    private final DataStore store;

    @Inject
    public HotelInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Hotel> find(UUID id) {
        return this.store.findAllHotels().stream()
                .filter(hotel -> hotel.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Hotel> findAll() {
        return this.store.findAllHotels();
    }

    @Override
    public void create(Hotel entity) {
        this.store.createHotel(entity);
    }

    @Override
    public void delete(Hotel entity) {
        this.store.deleteHotel(entity.getId());
    }

    @Override
    public void update(Hotel entity) {
        this.store.updateHotel(entity);
    }
}
