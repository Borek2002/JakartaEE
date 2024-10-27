package pl.edu.pg.eti.kask.hotel.model.function;

import pl.edu.pg.eti.kask.hotel.model.HotelModel;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationResponse;

import java.io.Serializable;
import java.util.function.Function;

public class HotelToModelFunction implements Function<Hotel, HotelModel>, Serializable {
    @Override
    public HotelModel apply(Hotel hotel) {
        return HotelModel.builder()
                .id(hotel.getId())
                .city(hotel.getCity())
                .name(hotel.getName())
                .rooms(hotel.getRooms())
                .reservationList(hotel.getReservations().stream()
                        .map(r->HotelModel.Reservation.builder()
                                .id(r.getId())
                                .build())
                        .toList()
                )
                .build();
    }
}
