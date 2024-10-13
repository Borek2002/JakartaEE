package pl.edu.pg.eti.kask.reservation.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.user.entity.User;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Reservation {

    public enum ReservationStatus{
        AVAILABLE, RESERVED, PAID, CANCELLED;
    }

    private LocalDate startTime;
    private LocalDate endTime;
    private ReservationStatus status;
    private User user;
    private Hotel hotel;
}
