package pl.edu.pg.eti.kask.user.mapper;

import pl.edu.pg.eti.kask.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.user.entity.User;

import java.util.List;
import java.util.function.Function;

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
