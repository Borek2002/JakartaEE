package pl.edu.pg.eti.kask.hotel.model.function;

import pl.edu.pg.eti.kask.hotel.model.HotelsModel;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;

import java.util.List;
import java.util.function.Function;

public class HotelsToModelFunction implements Function<List<Hotel>, HotelsModel> {
    @Override
    public HotelsModel apply(List<Hotel> hotelList) {
        return HotelsModel.builder()
                .hotels(hotelList.stream()
                        .map(hotel->HotelsModel.Hotel.builder()
                                .id(hotel.getId())
                                .name(hotel.getName())
                                .build()
                        ).toList()
                )
                .build();
    }
}
