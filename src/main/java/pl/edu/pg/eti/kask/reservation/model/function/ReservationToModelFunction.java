package pl.edu.pg.eti.kask.reservation.model.function;

import pl.edu.pg.eti.kask.reservation.model.ReservationModel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.io.Serializable;
import java.util.function.Function;

public class ReservationToModelFunction implements Function<Reservation, ReservationModel>, Serializable {
    @Override
    public ReservationModel apply(Reservation reservation) {
        return ReservationModel.builder()
                .id(reservation.getId())
                .hotel(reservation.getHotel().getName())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .status(reservation.getStatus())
                .build();
    }
}
