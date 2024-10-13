package pl.edu.pg.eti.kask.user.repository.api;

import pl.edu.pg.eti.kask.repository.api.Repository;
import pl.edu.pg.eti.kask.user.entity.User;

import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {

}
