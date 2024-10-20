package pl.edu.pg.eti.kask.hotel.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetHotelResponse {

    private UUID id;
    private String name;
    private int rooms;
    private String city;
}
