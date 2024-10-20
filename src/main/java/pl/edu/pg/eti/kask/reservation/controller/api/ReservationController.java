package pl.edu.pg.eti.kask.reservation.controller.api;

import pl.edu.pg.eti.kask.reservation.dto.GetReservationResponse;
import pl.edu.pg.eti.kask.reservation.dto.GetReservationsResponse;
import pl.edu.pg.eti.kask.reservation.dto.PatchReservationRequest;
import pl.edu.pg.eti.kask.reservation.dto.PutReservationRequest;

import java.util.UUID;

public interface ReservationController {

    GetReservationResponse getReservation(UUID id);

    GetReservationsResponse getReservations();

    void putReservation(UUID id, PutReservationRequest request);

    void patchReservation(UUID id, PatchReservationRequest request);

    void deleteReservation(UUID id);

}
