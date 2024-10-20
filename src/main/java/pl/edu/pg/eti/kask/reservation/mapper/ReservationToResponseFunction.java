package pl.edu.pg.eti.kask.reservation.mapper;

import pl.edu.pg.eti.kask.hotel.dto.GetHotelResponse;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationResponse;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.Objects;
import java.util.function.Function;

public class ReservationToResponseFunction implements Function<Reservation, GetReservationResponse> {
    @Override
    public GetReservationResponse apply(Reservation reservation) {
        return GetReservationResponse.builder()
                .id(reservation.getId())
                .hotel(Objects.nonNull(reservation.getHotel())?
                        GetReservationResponse.Hotel.builder()
                        .id(reservation.getHotel().getId())
                        .build():null)
                .user(Objects.nonNull(reservation.getUser())?
                        GetReservationResponse.User.builder()
                                .id(reservation.getUser().getId())
                                .build() : null)
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .status(reservation.getStatus())
                .build();
    }
}
