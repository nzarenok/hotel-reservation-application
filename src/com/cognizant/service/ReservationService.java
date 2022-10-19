package com.cognizant.service;

import com.cognizant.model.IRoom;
import com.cognizant.model.Reservation;

import java.util.*;

/**
 * Reservation Service class
 */
public class ReservationService {
    private static final ReservationService SINGLETON = new ReservationService();
    private final Map<String, List<Reservation>> reservations = new HashMap<>();
    private final Map<String, IRoom> roomMap = new HashMap<>();

    private ReservationService() {
    }

    /**
     * Gets instance of reservation service
     *
     * @return SINGLETON
     */
    public static ReservationService getInstance() {
        return SINGLETON;
    }

    /**
     * Adds room
     *
     * @param room room
     */
    public void addRoom(IRoom room) {
        roomMap.put(room.getRoomNumber(), room);
    }

    /**
     * Gets room
     *
     * @param roomID room ID
     * @return Map
     */
    public IRoom getRoom(String roomID) {
        return roomMap.get(roomID);
    }

    /**
     * Gets all rooms
     *
     * @return Map
     */
    public Map<String, IRoom> getAllRooms() {
        return roomMap;
    }

    /**
     * Gets customer reservation
     *
     * @param email email
     * @return Collection of reservation
     */
    public Collection<Reservation> getCustomerReservation(String email) {
        return reservations.get(email);
    }

    /**
     * Gets all reservations
     *
     * @return Map
     */
    public Map<String, List<Reservation>> getAllReservation() {
        return reservations;
    }

    /**
     * Adds reservation
     *
     * @param reservation reservation to add
     */
    void addReservation(Reservation reservation) {
        reservations.put(reservation.getCustomer().getEmail(), List.of(reservation));
    }

    /**
     * Reserves room
     *
     * @param reservation Reservation
     */
    public void reserveRoom(Reservation reservation) {
        final String customerEmail = reservation.getCustomer().getEmail();
        List<Reservation> customerReservations = reservations.get(customerEmail);

        if (customerReservations == null) {
            List<Reservation> list = new ArrayList<>();
            list.add(reservation);
            reservations.put(customerEmail, list);
        } else {
            customerReservations.add(reservation);
        }
    }

}
