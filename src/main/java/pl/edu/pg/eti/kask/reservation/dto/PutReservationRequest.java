package pl.edu.pg.eti.kask.reservation.dto;

import lombok.*;
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
public class PutReservationRequest {

    private UUID id;
    private LocalDate startTime;
    private LocalDate endTime;
    private Reservation.ReservationStatus status;
    private UUID hotelId;
    private UUID userId;
    private Long version;
}
