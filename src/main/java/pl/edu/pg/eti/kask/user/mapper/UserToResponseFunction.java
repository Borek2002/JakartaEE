package pl.edu.pg.eti.kask.user.mapper;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.util.function.Function;

@Dependent
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
