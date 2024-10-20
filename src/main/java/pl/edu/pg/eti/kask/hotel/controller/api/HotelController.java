package pl.edu.pg.eti.kask.hotel.controller.api;

import pl.edu.pg.eti.kask.hotel.dto.GetHotelResponse;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelsResponse;
import pl.edu.pg.eti.kask.hotel.dto.PatchHotelRequest;
import pl.edu.pg.eti.kask.hotel.dto.PutHotelRequest;

import java.util.UUID;

public interface HotelController {

    GetHotelResponse getHotel(UUID id);

    GetHotelsResponse getHotels();

    void putHotel(UUID id, PutHotelRequest request);

    void patchHotel(UUID id, PatchHotelRequest request);

    void deleteHotel(UUID id);
}
