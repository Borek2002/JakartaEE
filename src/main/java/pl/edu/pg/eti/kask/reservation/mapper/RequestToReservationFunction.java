package pl.edu.pg.eti.kask.reservation.mapper;

import pl.edu.pg.eti.kask.hotel.dto.PutHotelRequest;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.reservation.dto.PutReservationRequest;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToReservationFunction implements BiFunction<UUID, PutReservationRequest, Reservation> {
    @Override
    public Reservation apply(UUID uuid, PutReservationRequest request) {
        return Reservation.builder()
                .id(uuid)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(request.getStatus())
                .hotel(Hotel.builder()
                        .id(request.getHotelId())
                        .build())
                .user(User.builder()
                        .id(request.getUserId())
                        .build())
                .build();
    }
}
