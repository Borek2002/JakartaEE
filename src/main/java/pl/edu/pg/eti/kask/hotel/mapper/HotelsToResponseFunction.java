package pl.edu.pg.eti.kask.hotel.mapper;

import pl.edu.pg.eti.kask.hotel.dto.GetHotelsResponse;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;

import java.util.List;
import java.util.function.Function;

public class HotelsToResponseFunction implements Function<List<Hotel>, GetHotelsResponse> {
    @Override
    public GetHotelsResponse apply(List<Hotel> hotels) {
        return GetHotelsResponse.builder()
                .hotels(hotels.stream()
                        .map(h -> GetHotelsResponse.Hotel.builder()
                                .name(h.getName())
                                .id(h.getId())
                                .build())
                        .toList())
                .build();
    }
}
