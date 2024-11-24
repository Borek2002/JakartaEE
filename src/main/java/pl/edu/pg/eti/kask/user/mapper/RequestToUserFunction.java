package pl.edu.pg.eti.kask.user.mapper;

import pl.edu.pg.eti.kask.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.user.repository.entity.User;
import pl.edu.pg.eti.kask.user.repository.entity.UserRoles;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {
    @Override
    public User apply(UUID uuid, PutUserRequest putUserRequest) {
        return User.builder()
                .id(uuid)
                .email(putUserRequest.getEmail())
                .firstName(putUserRequest.getFirstName())
                .lastName(putUserRequest.getLastName())
                .password(putUserRequest.getPassword())
                .roles(List.of(UserRoles.USER))
                .build();
    }
}
