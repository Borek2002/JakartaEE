package pl.edu.pg.eti.kask.reservation.controller.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.component.DtoMapperFactory;
import pl.edu.pg.eti.kask.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.reservation.controller.api.ReservationController;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationResponse;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationsResponse;
import pl.edu.pg.eti.kask.reservation.dto.PatchReservationRequest;
import pl.edu.pg.eti.kask.reservation.dto.PutReservationRequest;
import pl.edu.pg.eti.kask.reservation.service.api.ReservationService;

import java.util.UUID;

@RequestScoped
public class ReservationDefaultController implements ReservationController {

    private final ReservationService service;

    private final DtoMapperFactory mapperFactory;

    @Inject
    public ReservationDefaultController(ReservationService service, DtoMapperFactory mapperFactory) {
        this.service = service;
        this.mapperFactory = mapperFactory;
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
    public void putReservation(UUID id, PutReservationRequest request) {
        try {
            this.service.create(this.mapperFactory.requestToReservation().apply(id, request));
        } catch (IllegalArgumentException ex){
            throw new BadRequestException(ex);
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
