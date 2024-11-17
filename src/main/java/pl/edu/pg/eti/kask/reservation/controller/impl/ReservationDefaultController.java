package pl.edu.pg.eti.kask.reservation.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.component.DtoMapperFactory;


import pl.edu.pg.eti.kask.hotel.controller.api.HotelController;
import pl.edu.pg.eti.kask.reservation.controller.api.ReservationController;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationResponse;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationsResponse;
import pl.edu.pg.eti.kask.reservation.dto.PatchReservationRequest;
import pl.edu.pg.eti.kask.reservation.dto.PutReservationRequest;
import pl.edu.pg.eti.kask.reservation.service.api.ReservationService;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class ReservationDefaultController implements ReservationController {

    private final ReservationService service;

    private final DtoMapperFactory mapperFactory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    @Inject
    public ReservationDefaultController(ReservationService service, DtoMapperFactory mapperFactory, UriInfo uriInfo) {
        this.service = service;
        this.mapperFactory = mapperFactory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetReservationResponse getReservation(UUID id) {
        return this.service.getReservation(id)
                .map(mapperFactory.reservationToResponse()).orElseThrow(NotFoundException::new);
    }

    @Override
    public GetReservationsResponse getReservations() {
        return this.mapperFactory.reservationsToResponse().apply(this.service.getAllReservations());
    }

    @Override
    @SneakyThrows
    public void putReservation(UUID hotelId, UUID id, PutReservationRequest request) {
        try {
            request.setHotelId(hotelId);
            this.service.create(this.mapperFactory.requestToReservation().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ReservationController.class, "getReservation")
                    .build(id)
                    .toString());
            //This can be done with Response builder but requires method different return type.
            //Calling HttpServletResponse#setStatus(int) is ignored.
            //Calling HttpServletResponse#sendError(int) causes response headers and body looking like error.
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex);
        }
        catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void patchReservation(UUID id, PatchReservationRequest request) {
        this.service.getReservation(id).ifPresentOrElse(
                reservation -> this.service.update(this.mapperFactory.updateToReservation().apply(reservation,request)),
                ()->{
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteReservation(UUID id) {
        this.service.getReservation(id).ifPresentOrElse(
                r->this.service.delete(r),
                ()->{
                    throw new NotFoundException();
                }
        );
    }
}
