package pl.edu.pg.eti.kask.reservation.model.function;

import pl.edu.pg.eti.kask.hotel.model.HotelModel;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.model.HotelModelSmall;

import java.io.Serializable;
import java.util.function.Function;

public class HotelModelSmallToFunction implements Function<Hotel, HotelModelSmall>, Serializable {
    @Override
    public HotelModelSmall apply(Hotel hotel) {
        return HotelModelSmall.builder()
                .id(hotel.getId())
                .city(hotel.getCity())
                .name(hotel.getName())
                .rooms(hotel.getRooms())
                .build();
    }
}
