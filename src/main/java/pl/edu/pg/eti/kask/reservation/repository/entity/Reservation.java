package pl.edu.pg.eti.kask.reservation.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.entity.VersionAndCreationDateAuditable;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.user.repository.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reservations")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Reservation extends VersionAndCreationDateAuditable implements Serializable {

    public enum ReservationStatus{
        AVAILABLE, RESERVED, PAID, CANCELLED;
    }
    @Id
    private UUID id;
    private LocalDate startTime;
    private LocalDate endTime;
    private ReservationStatus status;
    @ManyToOne
    private User user;
    @ManyToOne
    private Hotel hotel;
}
