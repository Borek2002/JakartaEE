package pl.edu.pg.eti.kask.hotel.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "hotels")
public class Hotel implements Serializable {

    @Id
    private UUID id;
    private String name;
    private int rooms;
    private String city;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
