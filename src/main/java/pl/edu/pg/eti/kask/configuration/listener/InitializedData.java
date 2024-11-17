package pl.edu.pg.eti.kask.configuration.listener;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;
import pl.edu.pg.eti.kask.user.repository.entity.User;
import pl.edu.pg.eti.kask.user.service.api.UserService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;


@ApplicationScoped
public class InitializedData {

    private final UserService userService;
    private final HotelService hotelService;
    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(UserService userService, HotelService hotelService, RequestContextController requestContextController) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.requestContextController = requestContextController;
    }



    @SneakyThrows
    private void init() {
        requestContextController.activate();
        if (userService.getAllUsers().isEmpty()) {
            User admin = User.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .firstName("System")
                    .lastName("Admin")
                    .email("admin@simplerpg.example.com")
                    .reservations(new ArrayList<>())
                    .build();

            User kevin = User.builder()
                    .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                    .firstName("Kevin")
                    .lastName("Pear")
                    .email("kevin@example.com")
                    .reservations(new ArrayList<>())
                    .build();

            User alice = User.builder()
                    .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                    .firstName("Alice")
                    .lastName("Grape")
                    .email("alice@example.com")
                    .reservations(new ArrayList<>())
                    .build();

            User jakub = User.builder()
                    .id(UUID.fromString("ff327e8a-77c0-4f9b-90a2-89e16895d1e1"))
                    .firstName("Jakub")
                    .lastName("Jacob")
                    .email("jakub@example.com")
                    .reservations(new ArrayList<>())
                    .build();

            userService.create(admin);
            userService.create(kevin);
            userService.create(alice);
            userService.create(jakub);

            Hotel hotel = Hotel.builder()
                    .id(UUID.fromString("ff327e8a-77c0-4f9b-90a2-89e16895d1e2"))
                    .city("WWA")
                    .rooms(4)
                    .name("WWAMelanz")
                    .build();
            hotelService.create(hotel);

        }
        requestContextController.deactivate();
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }


    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }
}
