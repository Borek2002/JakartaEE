package pl.edu.pg.eti.kask.reservation.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationResponse;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationsResponse;
import pl.edu.pg.eti.kask.reservation.dto.PatchReservationRequest;
import pl.edu.pg.eti.kask.reservation.dto.PutReservationRequest;

import java.util.UUID;

@Path("")
public interface ReservationController {

    @GET
    @Path("/reservations/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetReservationResponse getReservation(@PathParam("id")UUID id);

    @GET
    @Path("/reservations")
    @Produces(MediaType.APPLICATION_JSON)
    GetReservationsResponse getReservations();

    @PUT
    @Path("/hotels/{hotelId}/reservations/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putReservation(@PathParam("hotelId") UUID hotelId, @PathParam("id") UUID id, PutReservationRequest request);

    @PATCH
    @Path("/reservations/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchReservation(@PathParam("id")UUID id, PatchReservationRequest request);

    @DELETE
    @Path("/reservations/{id}")
    void deleteReservation(@PathParam("id")UUID id);

}
