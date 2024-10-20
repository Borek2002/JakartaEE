package pl.edu.pg.eti.kask.hotel.mapper;

import pl.edu.pg.eti.kask.hotel.dto.GetHotelResponse;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;

import java.util.function.Function;

public class HotelToResponseFunction implements Function<Hotel, GetHotelResponse> {
    @Override
    public GetHotelResponse apply(Hotel hotel) {
        return GetHotelResponse.builder()
                .id(hotel.getId())
                .city(hotel.getCity())
                .rooms(hotel.getRooms())
                .name(hotel.getName())
                .build();
    }
}
