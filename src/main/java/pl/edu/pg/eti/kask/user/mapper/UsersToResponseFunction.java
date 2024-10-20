package pl.edu.pg.eti.kask.user.mapper;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.util.List;
import java.util.function.Function;

@Dependent
public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {
    @Override
    public GetUsersResponse apply(List<User> users) {
        return GetUsersResponse.builder()
                .users(
                        users.stream().map(e->GetUsersResponse.User.builder()
                                .id(e.getId())
                                .email(e.getEmail())
                                .build())
                                .toList()
                )
                .build();
    }
}
