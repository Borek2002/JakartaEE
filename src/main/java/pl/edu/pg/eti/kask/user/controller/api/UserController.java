package pl.edu.pg.eti.kask.user.controller.api;

import pl.edu.pg.eti.kask.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.user.dto.GetUsersResponse;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {

    GetUsersResponse getAllUsers();

    GetUserResponse getUser(UUID uuid);

    byte[] getAvatar(UUID uuid);

    void createAvatar(UUID uuid, InputStream is);

    void updateAvatar(UUID uuid, InputStream is);

    void removeAvatar(UUID uuid);
}
