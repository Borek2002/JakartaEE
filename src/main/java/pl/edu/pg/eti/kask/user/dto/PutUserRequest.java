package pl.edu.pg.eti.kask.user.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutUserRequest {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}

