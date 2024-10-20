package pl.edu.pg.eti.kask.reservation.mapper;

import pl.edu.pg.eti.kask.hotel.dto.GetHotelsResponse;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationsResponse;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.List;
import java.util.function.Function;

public class ReservationsToResponseFunction implements Function<List<Reservation>, GetReservationsResponse> {
    @Override
    public GetReservationsResponse apply(List<Reservation> reservations) {
        return GetReservationsResponse.builder()
                .reservations(reservations.stream()
                        .map(r -> GetReservationsResponse.Reservation.builder()
                                .id(r.getId())
                                .startTime(r.getStartTime())
                                .build())
                        .toList())
                .build();
    }
}
