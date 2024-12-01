package pl.edu.pg.eti.kask.user.controller.impl;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.component.DtoMapperFactory;
import pl.edu.pg.eti.kask.user.controller.api.UserController;
import pl.edu.pg.eti.kask.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.user.repository.entity.User;
import pl.edu.pg.eti.kask.user.service.api.UserService;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class UserDefaultController implements UserController {

    private UserService service;
    private final DtoMapperFactory mapperFactory;
    private final UriInfo uriInfo;
    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        //ATM in this implementation only HttpServletRequest can be injected with CDI so JAX-RS injection is used.
        this.response = response;
    }

    @Inject
    public UserDefaultController(DtoMapperFactory mapperFactory, UriInfo uriInfo) {
        this.mapperFactory = mapperFactory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(UserService service) {
        this.service = service;
    }

    @Override
    public GetUsersResponse getAllUsers() {
        return this.mapperFactory.usersToResponse().apply(service.getAllUsers());
    }

    @Override
    public GetUserResponse getUser(UUID uuid) {
        return this.service.getUser(uuid)
                .map(this.mapperFactory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void createUser(UUID uuid, PutUserRequest request) {
        try {
            this.service.create(this.mapperFactory.requestToUserFunction().apply(uuid, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UserController.class, "getUser")
                    .build(uuid)
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
    public byte[] getAvatar(UUID uuid) {
        User user = this.service.getUser(uuid)
                .orElseThrow(NotFoundException::new);
        if (Objects.isNull(user.getPhoto())) {
            throw new NotFoundException("Uzytkownik nie ma avataru");
        }
        return service.getAvatar(java.nio.file.Path.of(user.getPhoto()));
    }

    @Override
    public void createAvatar(UUID uuid, InputStream is) {
        User user = this.service.getUser(uuid)
                .orElseThrow(NotFoundException::new);
        if (Objects.nonNull(user.getPhoto())) {
            throw new BadRequestException("Użytkonik ma już awatar");
        }
        this.service.createAvatar(user, is);
    }

//    @Override
//    public void updateAvatar(UUID uuid, InputStream is) {
//        User user = this.service.getUser(uuid)
//                .orElseThrow(NotFoundException::new);
//        if (Objects.isNull(user.getPhoto())){
//            throw new NotFoundException("Użytkownik nie posiada zdjęcia");
//        }
//        this.service.updateAvatar(user, is);
//    }
//
//    @Override
//    public void removeAvatar(UUID uuid) {
//        User user = this.service.getUser(uuid)
//                .orElseThrow(NotFoundException::new);
//        if (Objects.isNull(user.getPhoto())) {
//            throw new NotFoundException("Brak zdjecia");
//        }
//        this.service.removeAvatar(user);
//    }
}
