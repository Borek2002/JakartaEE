package pl.edu.pg.eti.kask.hotel.model;

import lombok.*;
import pl.edu.pg.eti.kask.hotel.dto.GetHotelsResponse;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class HotelsModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Hotel {
        private UUID id;
        private String name;
    }
    List<Hotel> hotels;
}
