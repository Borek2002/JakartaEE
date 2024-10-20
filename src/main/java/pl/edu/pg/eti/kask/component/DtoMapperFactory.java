package pl.edu.pg.eti.kask.component;

import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.hotel.mapper.HotelToResponseFunction;
import pl.edu.pg.eti.kask.hotel.mapper.HotelsToResponseFunction;
import pl.edu.pg.eti.kask.hotel.mapper.RequestToHotelFunction;
import pl.edu.pg.eti.kask.hotel.mapper.UpdateToHotelFunction;
import pl.edu.pg.eti.kask.reservation.mapper.RequestToReservationFunction;
import pl.edu.pg.eti.kask.reservation.mapper.ReservationToResponseFunction;
import pl.edu.pg.eti.kask.reservation.mapper.ReservationsToResponseFunction;
import pl.edu.pg.eti.kask.reservation.mapper.UpdateToReservationFunction;
import pl.edu.pg.eti.kask.user.mapper.UserToResponseFunction;
import pl.edu.pg.eti.kask.user.mapper.UsersToResponseFunction;

@ApplicationScoped
public class DtoMapperFactory {

    public UserToResponseFunction userToResponse(){return new UserToResponseFunction();}

    public UsersToResponseFunction usersToResponse(){return new UsersToResponseFunction();}

    public HotelsToResponseFunction hotelsToResponse(){return new HotelsToResponseFunction();}

    public HotelToResponseFunction hotelToResponse(){return new HotelToResponseFunction();}

    public RequestToHotelFunction requestToHotel(){return new RequestToHotelFunction();}

    public UpdateToHotelFunction updateToHotel(){return new UpdateToHotelFunction();}

    public ReservationToResponseFunction reservationToResponse() {return new ReservationToResponseFunction();}

    public ReservationsToResponseFunction reservationsToResponse() {return new ReservationsToResponseFunction();}

    public RequestToReservationFunction requestToReservation() {return new RequestToReservationFunction();}

    public UpdateToReservationFunction updateToReservation() {return new UpdateToReservationFunction();}
}
