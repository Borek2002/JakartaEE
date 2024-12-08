package pl.edu.pg.eti.kask.reservation.model.function;

import pl.edu.pg.eti.kask.reservation.model.ReservationEditModel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdateReservationWithModelFunction implements BiFunction<Reservation, ReservationEditModel, Reservation>, Serializable {
    @Override
    public Reservation apply(Reservation reservation, ReservationEditModel reservationEditModel) {
        return Reservation.builder()
                .id(reservation.getId())
                .hotel(reservation.getHotel())
                .endTime(reservationEditModel.getEndTime())
                .status(reservationEditModel.getStatus())
                .startTime(reservation.getStartTime())
                .user(reservation.getUser())
                .creationDateTime(reservation.getCreationDateTime())
                .version(reservation.getVersion())
                .build();
    }
}
