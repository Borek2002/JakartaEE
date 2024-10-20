package pl.edu.pg.eti.kask.hotel.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.component.DtoMapperFactory;
import pl.edu.pg.eti.kask.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.hotel.controller.api.HotelController;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelResponse;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelsResponse;
import pl.edu.pg.eti.kask.hotel.dto.PatchHotelRequest;
import pl.edu.pg.eti.kask.hotel.dto.PutHotelRequest;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;

import java.util.UUID;

@RequestScoped
public class HotelDefaultController implements HotelController {

    private final HotelService service;
    private final DtoMapperFactory mapperFactory;

    @Inject
    public HotelDefaultController(HotelService service, DtoMapperFactory mapperFactory) {
        this.service = service;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public GetHotelResponse getHotel(UUID id) {
        return this.service.getHotel(id).map(mapperFactory.hotelToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    public GetHotelsResponse getHotels() {
        return this.mapperFactory.hotelsToResponse().apply(this.service.getHotels());
    }

    @Override
    public void putHotel(UUID id, PutHotelRequest request) {
        try {
            this.service.create(this.mapperFactory.requestToHotel().apply(id,request));
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchHotel(UUID id, PatchHotelRequest request) {
        this.service.getHotel(id).ifPresentOrElse(
                hotel -> this.service.update(this.mapperFactory.updateToHotel().apply(hotel, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteHotel(UUID id) {
        this.service.getHotel(id).ifPresentOrElse(
                hotel -> this.service.delete(hotel),
                () ->{
                    throw new NotFoundException();
                }
        );
    }
}
