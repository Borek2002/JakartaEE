package pl.edu.pg.eti.kask.reservation.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.hotel.model.HotelModel;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;
import pl.edu.pg.eti.kask.reservation.model.ReservationModel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.reservation.service.api.ReservationService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ReservationView implements Serializable {

    private ReservationService service;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private ReservationModel reservation;

    @Inject
    public ReservationView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setReservationService(ReservationService reservationService) {
        this.service = reservationService;
    }

    public void init() throws IOException {
        Optional<Reservation> reservation = service.getReservation(this.id);
        if(reservation.isPresent()){
            this.reservation = factory.reservationToModel().apply(reservation.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Hotel not found");
        }
    }
}
