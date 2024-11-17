package pl.edu.pg.eti.kask.hotel.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelResponse;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelsResponse;
import pl.edu.pg.eti.kask.hotel.dto.PatchHotelRequest;
import pl.edu.pg.eti.kask.hotel.dto.PutHotelRequest;

import java.util.UUID;

@Path("")
public interface HotelController {

    @GET
    @Path("/hotels/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetHotelResponse getHotel(@PathParam("id") UUID id);

    @GET
    @Path("/hotels")
    @Produces(MediaType.APPLICATION_JSON)
    GetHotelsResponse getHotels();

    @PUT
    @Path("/hotels/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putHotel(@PathParam("id") UUID id, PutHotelRequest request);

    @PATCH
    @Path("/hotels/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchHotel(@PathParam("id") UUID id, PatchHotelRequest request);

    @DELETE
    @Path("/hotels/{id}")
    void deleteHotel(@PathParam("id") UUID id);
}
