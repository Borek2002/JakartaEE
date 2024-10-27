package pl.edu.pg.eti.kask.reservation.model;

import lombok.*;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ReservationEditModel {


    private LocalDate endTime;
    private Reservation.ReservationStatus status;
}
