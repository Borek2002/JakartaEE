package pl.edu.pg.eti.kask.component;

import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.hotel.model.function.HotelToModelFunction;
import pl.edu.pg.eti.kask.hotel.model.function.HotelsToModelFunction;
import pl.edu.pg.eti.kask.reservation.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {

    public HotelToModelFunction hotelToModel() {
        return new HotelToModelFunction();
    }

    public HotelsToModelFunction hotelsToModel() {
        return new HotelsToModelFunction();
    }
    public HotelModelSmallToFunction hotelModelSmallToModel(){return new HotelModelSmallToFunction();}

    public ModelToReservationFunction modelToReservation() {
        return new ModelToReservationFunction();
    }

    public ReservationToEditModelFunction reservationToEditModel() {
        return new ReservationToEditModelFunction();
    }

    public ReservationToModelFunction reservationToModel() {
        return new ReservationToModelFunction();
    }

    public UpdateReservationWithModelFunction updateReservation() {return new UpdateReservationWithModelFunction();}
}
