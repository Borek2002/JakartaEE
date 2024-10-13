package pl.edu.pg.eti.kask.configuration.listener;

import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.datastore.component.DataStore;
import pl.edu.pg.eti.kask.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.user.repository.memory.UserInMemoryRepository;
import pl.edu.pg.eti.kask.user.service.api.UserService;
import pl.edu.pg.eti.kask.user.service.impl.UserDefaultService;

import java.nio.file.Path;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        UserRepository userRepository = new UserInMemoryRepository(dataSource);
        Path photoDirectory = Path.of("C:\\Users\\Damian\\Pictures\\TEMP");
        event.getServletContext().setAttribute("userService", new UserDefaultService(userRepository, photoDirectory));
    }
}
