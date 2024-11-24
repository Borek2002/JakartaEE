package pl.edu.pg.eti.kask.user.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;

import java.io.Serializable;
import java.nio.file.Path;
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
@Table(name="users")
public class User implements Serializable {

    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    @ToString.Exclude
    private String password;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String photo;
    @CollectionTable(name = "users__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
