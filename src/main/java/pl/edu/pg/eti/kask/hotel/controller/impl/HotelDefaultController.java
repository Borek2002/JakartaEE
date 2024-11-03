package pl.edu.pg.eti.kask.hotel.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.component.DtoMapperFactory;
import pl.edu.pg.eti.kask.hotel.controller.api.HotelController;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelResponse;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelsResponse;
import pl.edu.pg.eti.kask.hotel.dto.PatchHotelRequest;
import pl.edu.pg.eti.kask.hotel.dto.PutHotelRequest;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;

import java.util.UUID;

@Path("")
public class HotelDefaultController implements HotelController {

    private final HotelService service;
    private final DtoMapperFactory mapperFactory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    @Inject
    public HotelDefaultController(HotelService service, DtoMapperFactory mapperFactory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.mapperFactory = mapperFactory;
        this.uriInfo = uriInfo;
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
    @SneakyThrows
    public void putHotel(UUID id, PutHotelRequest request) {
        try {
            this.service.create(this.mapperFactory.requestToHotel().apply(id,request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(HotelController.class, "getHotel")
                    .build(id)
                    .toString());
            //This can be done with Response builder but requires method different return type.
            //Calling HttpServletResponse#setStatus(int) is ignored.
            //Calling HttpServletResponse#sendError(int) causes response headers and body looking like error.
            throw new WebApplicationException(Response.Status.CREATED);
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
