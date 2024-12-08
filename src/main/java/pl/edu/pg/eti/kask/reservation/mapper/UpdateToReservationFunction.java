package pl.edu.pg.eti.kask.reservation.mapper;

import pl.edu.pg.eti.kask.hotel.dto.PatchHotelRequest;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.dto.PatchReservationRequest;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.util.function.BiFunction;

public class UpdateToReservationFunction implements BiFunction<Reservation, PatchReservationRequest, Reservation> {

    @Override
    public Reservation apply(Reservation reservation, PatchReservationRequest patchReservationRequest) {
        return Reservation.builder()
                .id(reservation.getId())
                .user(reservation.getUser())
                .hotel(reservation.getHotel())
                .endTime(patchReservationRequest.getEndTime())
                .status(patchReservationRequest.getStatus())
                .startTime(reservation.getStartTime())
                .version(reservation.getVersion())
                .creationDateTime(reservation.getCreationDateTime())
                .build();
    }
}
