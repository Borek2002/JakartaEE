package pl.edu.pg.eti.kask.user.mapper;

import pl.edu.pg.eti.kask.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {
    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
