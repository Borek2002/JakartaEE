package pl.edu.pg.eti.kask.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.edu.pg.eti.kask.hotel.controller.api.HotelController;
import pl.edu.pg.eti.kask.hotel.dto.PatchHotelRequest;
import pl.edu.pg.eti.kask.hotel.dto.PutHotelRequest;
import pl.edu.pg.eti.kask.reservation.controller.api.ReservationController;
import pl.edu.pg.eti.kask.reservation.dto.PatchReservationRequest;
import pl.edu.pg.eti.kask.reservation.dto.PutReservationRequest;
import pl.edu.pg.eti.kask.user.controller.api.UserController;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private final UserController userController;

    private final HotelController hotelController;

    private final ReservationController reservationController;

    public static final class Paths {

        public static final String API = "/api";

    }

    public static final class Patterns {

        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern USERS = Pattern.compile("/users/?");

        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));

        public static final Pattern HOTELS = Pattern.compile("/hotels/?");

        public static final Pattern HOTEL = Pattern.compile("/hotels/(%s)".formatted(UUID.pattern()));

        public static final Pattern RESERVATIONS = Pattern.compile("/reservations/?");

        public static final Pattern RESERVATION = Pattern.compile("/reservations/(%s)".formatted(UUID.pattern()));


        public static final Pattern USER_PHOTO = Pattern.compile("/users/(%s)/photo".formatted(UUID.pattern()));

    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(UserController userController, HotelController hotelController, ReservationController reservationController) {
        this.userController = userController;
        this.hotelController = hotelController;
        this.reservationController = reservationController;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getAllUsers()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                return;
            } else if (path.matches(Patterns.USER_PHOTO.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.USER_PHOTO, path);
                byte[] portrait = userController.getAvatar(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            } else if (path.matches(Patterns.HOTELS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(hotelController.getHotels()));
                return;
            } else if (path.matches(Patterns.HOTEL.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.HOTEL, path);
                response.getWriter().write(jsonb.toJson(hotelController.getHotel(uuid)));
                return;
            } else if (path.matches(Patterns.RESERVATIONS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(reservationController.getReservations()));
                return;
            } else if (path.matches(Patterns.RESERVATION.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.RESERVATION, path);
                response.getWriter().write(jsonb.toJson(reservationController.getReservation(uuid)));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER_PHOTO.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_PHOTO, path);
                userController.createAvatar(uuid, request.getPart("photo").getInputStream());
                return;
            } else if (path.matches(Patterns.HOTEL.pattern())) {
                UUID uuid = extractUuid(Patterns.HOTEL, path);
                hotelController.putHotel(uuid, jsonb.fromJson(request.getReader(), PutHotelRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "hotels", uuid.toString()));
                return;
            } else if (path.matches(Patterns.RESERVATION.pattern())) {
                UUID uuid = extractUuid(Patterns.RESERVATION, path);
                reservationController.putReservation(uuid, jsonb.fromJson(request.getReader(), PutReservationRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "reservations", uuid.toString()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER_PHOTO.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_PHOTO, path);
                userController.updateAvatar(uuid, request.getPart("photo").getInputStream());
                return;
            } else if (path.matches(Patterns.HOTEL.pattern())) {
                UUID uuid = extractUuid(Patterns.HOTEL, path);
                hotelController.patchHotel(uuid, jsonb.fromJson(request.getReader(), PatchHotelRequest.class));
                return;
            } else if (path.matches(Patterns.RESERVATION.pattern())) {
                UUID uuid = extractUuid(Patterns.RESERVATION, path);
                reservationController.patchReservation(uuid, jsonb.fromJson(request.getReader(), PatchReservationRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER_PHOTO.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_PHOTO, path);
                userController.removeAvatar(uuid);
                return;
            } else if (path.matches(Patterns.HOTEL.pattern())) {
                UUID uuid = extractUuid(Patterns.HOTEL, path);
                hotelController.deleteHotel(uuid);
                return;
            } else if (path.matches(Patterns.RESERVATION.pattern())) {
                UUID uuid = extractUuid(Patterns.RESERVATION, path);
                reservationController.deleteReservation(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }
}
