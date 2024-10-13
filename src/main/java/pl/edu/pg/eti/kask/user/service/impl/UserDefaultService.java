package pl.edu.pg.eti.kask.user.service.impl;

import pl.edu.pg.eti.kask.user.entity.User;
import pl.edu.pg.eti.kask.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.user.service.api.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDefaultService implements UserService {

    private final UserRepository repository;
    private final Path photoDirectory;

    public UserDefaultService(UserRepository repository, Path photoDirectory) {
        this.repository = repository;
        this.photoDirectory = photoDirectory;
    }

    @Override
    public Optional<User> getUser(UUID uuid) {
        return this.repository.find(uuid);
    }

    @Override
    public List<User> getAllUsers() {
        return this.repository.findAll();
    }

    @Override
    public void create(User user) {
        this.repository.create(user);
    }

    @Override
    public byte[] getAvatar(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createAvatar(User user, InputStream is) {
        try {
            Files.createDirectories(photoDirectory);
            Path photoPath = photoDirectory.resolve(user.getId().toString() + ".png");
            Files.copy(is, photoPath, StandardCopyOption.REPLACE_EXISTING);
            user.setPhoto(String.valueOf(photoPath));
            repository.update(user);
        } catch (IOException e) {
            throw new RuntimeException("Nie mozna zapisac awataru", e);
        }
    }

    @Override
    public void updateAvatar(User user, InputStream is) {
        try {
            Path photoPath = photoDirectory.resolve(user.getId().toString() + ".png");
            Files.copy(is, photoPath, StandardCopyOption.REPLACE_EXISTING);
            user.setPhoto(String.valueOf(photoPath));
            repository.update(user);
        } catch (IOException e) {
            throw new RuntimeException("Nie mozna zmienić awataru", e);
        }
    }

    @Override
    public void removeAvatar(User user) {
        try {
            Files.delete(Path.of(user.getPhoto()));
            user.setPhoto(null);
            repository.update(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
