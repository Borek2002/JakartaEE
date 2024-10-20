package pl.edu.pg.eti.kask.user.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Reservation> reservations = new ArrayList<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String photo;
}
