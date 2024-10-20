package pl.edu.pg.eti.kask.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.component.DtoMapperFactory;
import pl.edu.pg.eti.kask.user.controller.api.UserController;
import pl.edu.pg.eti.kask.user.controller.impl.UserDefaultController;
import pl.edu.pg.eti.kask.user.service.api.UserService;


public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");

        event.getServletContext().setAttribute("userController", new UserDefaultController(userService, new DtoMapperFactory()) {
        });
    }
}
