package pl.edu.pg.eti.kask.user.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface UserController {

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getAllUsers();

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID uuid);

    @PUT
    @Path("/users/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void createUser(@PathParam("id") UUID uuid, PutUserRequest request);

    @GET
    @Path("/users/{id}/photo")
    @Produces(MediaType.APPLICATION_JSON)
    byte[] getAvatar(@PathParam("id") UUID uuid);

    @PUT
    @Path("/users/{id}/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void createAvatar(@PathParam("id") UUID uuid, @SuppressWarnings("RestParamTypeInspection")@FormParam("is") InputStream is);

    @PATCH
    @Path("/hotels/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    void updateAvatar(@PathParam("id") UUID uuid,  @SuppressWarnings("RestParamTypeInspection")@FormParam("is") InputStream is);

    @DELETE
    @Path("/hotels/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void removeAvatar(@PathParam("id") UUID uuid);
}
