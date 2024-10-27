package pl.edu.pg.eti.kask.reservation.model;

import lombok.*;
import pl.edu.pg.eti.kask.hotel.model.HotelModel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ReservationCreateModel {

    private UUID id;
    private LocalDate startTime;
    private LocalDate endTime;
    private Reservation.ReservationStatus status;
    private HotelModelSmall hotel;
}
