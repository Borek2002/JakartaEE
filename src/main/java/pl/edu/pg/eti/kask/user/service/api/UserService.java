package pl.edu.pg.eti.kask.user.service.api;

import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> getUser(UUID uuid);

    List<User> getAllUsers();

    void create(User user);

    byte[] getAvatar(Path path);

    void createAvatar(User user, InputStream is);

    void updateAvatar(User user, InputStream is);

    void removeAvatar(User user);
}
