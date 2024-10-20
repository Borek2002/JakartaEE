package pl.edu.pg.eti.kask.hotel.mapper;

import pl.edu.pg.eti.kask.hotel.dto.GetHotelsResponse;
import pl.edu.pg.eti.kask.hotel.dto.PutHotelRequest;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToHotelFunction implements BiFunction<UUID, PutHotelRequest, Hotel> {
    @Override
    public Hotel apply(UUID uuid, PutHotelRequest request) {
        return Hotel.builder()
                .id(uuid)
                .city(request.getCity())
                .rooms(request.getRooms())
                .name(request.getName())
                .reservations(new ArrayList<>())
                .build();
    }
}
