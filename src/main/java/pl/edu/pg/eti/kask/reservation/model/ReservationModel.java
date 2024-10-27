package pl.edu.pg.eti.kask.reservation.model;

import lombok.*;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationResponse;
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
public class ReservationModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Hotel {

        private UUID id;
    }

    private UUID id;
    private LocalDate startTime;
    private LocalDate endTime;
    private Reservation.ReservationStatus status;
    private String hotel;
}
