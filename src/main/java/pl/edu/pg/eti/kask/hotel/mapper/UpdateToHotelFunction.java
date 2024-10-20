package pl.edu.pg.eti.kask.hotel.mapper;

import pl.edu.pg.eti.kask.hotel.dto.PatchHotelRequest;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;

import java.util.UUID;
import java.util.function.BiFunction;

public class UpdateToHotelFunction implements BiFunction<Hotel, PatchHotelRequest, Hotel> {

    @Override
    public Hotel apply(Hotel hotel, PatchHotelRequest patchHotelRequest) {
        return Hotel.builder()
                .id(hotel.getId())
                .name(patchHotelRequest.getName())
                .rooms(patchHotelRequest.getRooms())
                .city(hotel.getCity())
                .build();
    }
}
