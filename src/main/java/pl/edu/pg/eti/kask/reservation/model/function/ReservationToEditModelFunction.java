package pl.edu.pg.eti.kask.reservation.model.function;

import pl.edu.pg.eti.kask.reservation.model.ReservationEditModel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.io.Serializable;
import java.util.function.Function;

public class ReservationToEditModelFunction implements Function<Reservation, ReservationEditModel>, Serializable {
    @Override
    public ReservationEditModel apply(Reservation reservation) {
        return ReservationEditModel.builder()
                .endTime(reservation.getEndTime())
                .status(reservation.getStatus())
                .build();
    }
}
