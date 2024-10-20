package pl.edu.pg.eti.kask.reservation.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Reservation implements Serializable {

    public enum ReservationStatus{
        AVAILABLE, RESERVED, PAID, CANCELLED;
    }

    private UUID id;
    private LocalDate startTime;
    private LocalDate endTime;
    private ReservationStatus status;
    private User user;
    private Hotel hotel;
}
