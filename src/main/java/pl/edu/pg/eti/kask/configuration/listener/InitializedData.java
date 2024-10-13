package pl.edu.pg.eti.kask.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.user.entity.User;
import pl.edu.pg.eti.kask.user.service.api.UserService;

import java.io.InputStream;
import java.util.UUID;

@WebListener
public class InitializedData implements ServletContextListener {

    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        init();
    }

    @SneakyThrows
    private void init() {
        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .firstName("System")
                .lastName("Admin")
                .email("admin@simplerpg.example.com")
                .build();

        User kevin = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .firstName("Kevin")
                .lastName("Pear")
                .email("kevin@example.com")
                .build();

        User alice = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .firstName("Alice")
                .lastName("Grape")
                .email("alice@example.com")
                .build();

        User jakub = User.builder()
                .id(UUID.fromString("ff327e8a-77c0-4f9b-90a2-89e16895d1e1"))
                .firstName("Jakub")
                .lastName("Jacob")
                .email("jakub@example.com")
                .build();

        userService.create(admin);
        userService.create(kevin);
        userService.create(alice);
        userService.create(jakub);
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
