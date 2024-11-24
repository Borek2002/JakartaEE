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
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;
import pl.edu.pg.eti.kask.reservation.model.ReservationEditModel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.reservation.service.api.ReservationService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class ReservationEdit implements Serializable {

    private ReservationService reservationService;
    private final ModelFunctionFactory factory;

    /**
     * Character id.
     */
    @Setter
    @Getter
    private UUID id;

    @Getter
    private ReservationEditModel reservation;

    @Getter
    private final List<Reservation.ReservationStatus> statuses = List.of(Reservation.ReservationStatus.values());

    @Inject
    public ReservationEdit(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void init() throws IOException {
        Optional<Reservation> reservation = this.reservationService.getReservation(id);
        if (reservation.isPresent()) {
            this.reservation = factory.reservationToEditModel().apply(reservation.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Character not found");
        }
    }

    public String saveAction() {
        reservationService.update(factory.updateReservation().apply(reservationService.getReservation(id).orElseThrow(), reservation));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
