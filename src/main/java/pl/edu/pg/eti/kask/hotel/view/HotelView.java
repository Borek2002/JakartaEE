package pl.edu.pg.eti.kask.hotel.view;

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
import pl.edu.pg.eti.kask.hotel.model.HotelsModel;
import pl.edu.pg.eti.kask.hotel.repository.entity.Hotel;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;
import pl.edu.pg.eti.kask.reservation.service.api.ReservationService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class HotelView implements Serializable {

    private HotelService service;
    private ReservationService reservationService;
    private final ModelFunctionFactory factory;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private HotelModel hotel;

    @Inject
    public HotelView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setHotelService(HotelService hotelService) {
        this.service = hotelService;
    }

    @EJB
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void init() throws IOException {
        Optional<Hotel> hotel = service.getHotel(id);
        if (hotel.isPresent()) {
            this.hotel = factory.hotelToModel().apply(hotel.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Hotel not found");
        }
    }

    public String delete(HotelModel.Reservation reservation) {
        this.reservationService.delete(reservationService.getReservation(reservation.getId()).get());
        return "hotel_view?id="+ this.id +"&faces-redirect=true";
    }

}
