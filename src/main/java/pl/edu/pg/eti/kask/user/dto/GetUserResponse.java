package pl.edu.pg.eti.kask.user.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUserResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
