package pl.edu.pg.eti.kask.configuration.listener;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;
import pl.edu.pg.eti.kask.user.repository.entity.User;
import pl.edu.pg.eti.kask.user.repository.entity.UserRoles;
import pl.edu.pg.eti.kask.user.service.api.UserService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
@DependsOn("InitializeAdminService")
@DeclareRoles({UserRoles.ADMIN, UserRoles.USER})
@RunAs(UserRoles.ADMIN)
@Log
public class InitializedData {

    private UserService userService;
    private HotelService hotelService;

    @Inject
    private SecurityContext securityContext;

    @EJB
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userService.getUser(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6")).isEmpty()) {
            User admin = User.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .firstName("System")
                    .lastName("Admin")
                    .email("admin@simplerpg.example.com")
                    .password("adminadmin")
                    .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                    .reservations(new ArrayList<>())
                    .build();

            User kevin = User.builder()
                    .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                    .firstName("Kevin")
                    .lastName("Pear")
                    .email("kevin@example.com")
                    .password("adminadmin")
                    .roles(List.of(UserRoles.USER))
                    .reservations(new ArrayList<>())
                    .build();

            User alice = User.builder()
                    .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                    .firstName("Alice")
                    .lastName("Grape")
                    .email("alice@example.com")
                    .password("adminadmin")
                    .roles(List.of(UserRoles.USER))
                    .reservations(new ArrayList<>())
                    .build();

            User jakub = User.builder()
                    .id(UUID.fromString("ff327e8a-77c0-4f9b-90a2-89e16895d1e1"))
                    .firstName("Jakub")
                    .lastName("Jacob")
                    .email("jakub@example.com")
                    .password("adminadmin")
                    .roles(List.of(UserRoles.USER))
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
            Hotel hotel1 = Hotel.builder()
                    .id(UUID.fromString("ff327e8a-77c0-4f9b-90a2-89e16895d1e8"))
                    .city("Kato")
                    .rooms(4)
                    .name("Hello")
                    .build();
            hotelService.create(hotel1);

        }
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
