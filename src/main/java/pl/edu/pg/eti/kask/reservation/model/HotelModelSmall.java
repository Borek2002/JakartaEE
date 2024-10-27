package pl.edu.pg.eti.kask.reservation.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class HotelModelSmall {

    private UUID id;
    private String name;
    private int rooms;
    private String city;
}
