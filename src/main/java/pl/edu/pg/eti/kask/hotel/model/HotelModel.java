package pl.edu.pg.eti.kask.hotel.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class HotelModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Reservation {
        private UUID id;
    }

    private UUID id;
    private String name;
    private int rooms;
    private String city;
    List<Reservation> reservationList;
}
