package pl.edu.pg.eti.kask.reservation.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.hotel.model.HotelModel;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;
import pl.edu.pg.eti.kask.reservation.model.HotelModelSmall;
import pl.edu.pg.eti.kask.reservation.model.ReservationCreateModel;
import pl.edu.pg.eti.kask.reservation.repository.entity.Reservation;
import pl.edu.pg.eti.kask.reservation.service.api.ReservationService;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class ReservationCreate implements Serializable {

    private ReservationService reservationService;
    private HotelService hotelService;
    private final ModelFunctionFactory modelFunctionFactory;
    private final Conversation conversation;

    @Getter
    private ReservationCreateModel reservation;

    @Getter
    private List<HotelModelSmall> hotels;

    @Getter
    private final List<Reservation.ReservationStatus> statuses = List.of(Reservation.ReservationStatus.values());
    @Inject
    public ReservationCreate(ModelFunctionFactory modelFunctionFactory, Conversation conversation) {
        this.modelFunctionFactory = modelFunctionFactory;
        this.conversation = conversation;
    }

    @EJB
    public void setHotelService(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @EJB
    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void init() {
        this.hotels = this.hotelService.getHotels().stream()
                .map(modelFunctionFactory.hotelModelSmallToModel())
                .collect(Collectors.toList());
        this.reservation = ReservationCreateModel.builder()
                .id(UUID.randomUUID())
                .build();
        conversation.begin();
    }

    public String saveActionn() {
        System.out.println(reservation.toString());
        this.reservationService.createForCallerPrincipal(modelFunctionFactory.modelToReservation().apply(reservation));
        conversation.end();
        return "/hotel/hotel_list.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/hotel/hotel_list.xhtml?faces-redirect=true";
    }
}
