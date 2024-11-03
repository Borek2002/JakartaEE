package pl.edu.pg.eti.kask.user.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.BadRequestException;
import pl.edu.pg.eti.kask.component.DtoMapperFactory;
import pl.edu.pg.eti.kask.user.controller.api.UserController;
import pl.edu.pg.eti.kask.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.user.repository.entity.User;
import pl.edu.pg.eti.kask.user.service.api.UserService;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@RequestScoped
public class UserDefaultController implements UserController {

    private final UserService userService;

    private final DtoMapperFactory mapperFactory;

    @Inject
    public UserDefaultController(UserService userService, DtoMapperFactory mapperFactory) {
        this.userService = userService;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public GetUsersResponse getAllUsers() {
        return this.mapperFactory.usersToResponse().apply(userService.getAllUsers());
    }

    @Override
    public GetUserResponse getUser(UUID uuid) {
        return this.userService.getUser(uuid)
                .map(this.mapperFactory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public byte[] getAvatar(UUID uuid) {
        User user = this.userService.getUser(uuid)
                .orElseThrow(NotFoundException::new);
        if (Objects.isNull(user.getPhoto())) {
            throw new NotFoundException("Uzytkownik nie ma avataru");
        }
        return userService.getAvatar(Path.of(user.getPhoto()));
    }

    @Override
    public void createAvatar(UUID uuid, InputStream is) {
        User user = this.userService.getUser(uuid)
                .orElseThrow(NotFoundException::new);
        if (Objects.nonNull(user.getPhoto())) {
            throw new BadRequestException("Użytkonik ma już awatar");
        }
        this.userService.createAvatar(user, is);
    }

    @Override
    public void updateAvatar(UUID uuid, InputStream is) {
        User user = this.userService.getUser(uuid)
                .orElseThrow(NotFoundException::new);
        if (Objects.isNull(user.getPhoto())){
            throw new NotFoundException("Użytkownik nie posiada zdjęcia");
        }
        this.userService.updateAvatar(user, is);
    }

    @Override
    public void removeAvatar(UUID uuid) {
        User user = this.userService.getUser(uuid)
                .orElseThrow(NotFoundException::new);
        if (Objects.isNull(user.getPhoto())) {
            throw new NotFoundException("Brak zdjecia");
        }
        this.userService.removeAvatar(user);
    }
}
