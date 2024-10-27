package pl.edu.pg.eti.kask.reservation.model.function;

import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.model.ReservationCreateModel;
import pl.edu.pg.eti.kask.reservation.model.ReservationModel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToReservationFunction implements Function<ReservationCreateModel, Reservation>, Serializable {
    @Override
    public Reservation apply(ReservationCreateModel reservationCreateModel) {
        return Reservation.builder()
                .id(reservationCreateModel.getId())
                .startTime(reservationCreateModel.getStartTime())
                .endTime(reservationCreateModel.getEndTime())
                .hotel(Hotel.builder()
                        .id(reservationCreateModel.getHotel().getId())
                        .build())
                .build();
    }
}
