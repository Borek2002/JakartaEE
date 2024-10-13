package pl.edu.pg.eti.kask.hotel.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Hotel {

    private String name;
    private int rooms;
    private String city;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Reservation> reservations;
}
