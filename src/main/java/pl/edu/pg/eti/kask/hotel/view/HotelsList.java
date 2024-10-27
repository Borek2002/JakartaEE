package pl.edu.pg.eti.kask.hotel.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.hotel.model.HotelsModel;
import pl.edu.pg.eti.kask.hotel.service.api.HotelService;

import java.util.Objects;

@RequestScoped
@Named
public class HotelsList {

    private final HotelService service;
    private final ModelFunctionFactory factory;
    private HotelsModel hotels;

    @Inject
    public HotelsList(HotelService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public HotelsModel getHotels() {
        if (Objects.isNull(hotels)) {
            hotels = factory.hotelsToModel().apply(service.getHotels());
        }
        return hotels;
    }

    public String delete(HotelsModel.Hotel hotel) {
        service.delete(this.service.getHotel(hotel.getId()).get());
        return "hotel_list?faces-redirect=true";
    }
}
