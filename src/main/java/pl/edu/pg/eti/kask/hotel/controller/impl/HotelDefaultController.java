package pl.edu.pg.eti.kask.hotel.controller.impl;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.component.DtoMapperFactory;
import pl.edu.pg.eti.kask.hotel.controller.api.HotelController;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelResponse;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelsResponse;
import pl.edu.pg.eti.kask.hotel.dto.PatchHotelRequest;
import pl.edu.pg.eti.kask.hotel.dto.PutHotelRequest;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationsResponse;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.reservation.service.api.ReservationService;
import pl.edu.pg.eti.kask.user.repository.entity.UserRoles;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class HotelDefaultController implements HotelController {

    private HotelService service;
    private final DtoMapperFactory mapperFactory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    @Inject
    public HotelDefaultController(DtoMapperFactory mapperFactory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.mapperFactory = mapperFactory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(HotelService service) {
        this.service = service;
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public GetHotelResponse getHotel(UUID id) {
        return this.service.getHotel(id).map(mapperFactory.hotelToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public GetHotelsResponse getHotels() {
        return this.mapperFactory.hotelsToResponse().apply(this.service.getHotels());
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public GetReservationsResponse getHotelsReservations(UUID id) {
        Hotel hotel = this.service.getHotel(id).orElseThrow(NotFoundException::new);
        return this.mapperFactory.reservationsToResponse().apply(hotel.getReservations());
    }

    @Override
    @RolesAllowed(UserRoles.ADMIN)
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
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
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
    @RolesAllowed(UserRoles.ADMIN)
    public void deleteHotel(UUID id) {
        this.service.getHotel(id).ifPresentOrElse(
                entity -> {
                    try {
                        service.delete(entity);
                    } catch (EJBAccessException ex) {
                        log.log(Level.WARNING, ex.getMessage(), ex);
                        throw new ForbiddenException(ex.getMessage());
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
