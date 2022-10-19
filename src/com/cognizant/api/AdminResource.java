package com.cognizant.api;

import com.cognizant.model.*;
import com.cognizant.service.CustomerService;
import com.cognizant.service.ReservationService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Admin Resource class
 */
public class AdminResource {
    private static final AdminResource SINGLETON = new AdminResource();

    private AdminResource() {
    }

    /**
     * Gets instance of admin resource class
     *
     * @return SINGLETON
     */
    public static AdminResource getInstance() {
        return SINGLETON;
    }

    /**
     * Gets customer
     *
     * @param email
     * @return Customer's email
     */
    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    /**
     * Adds room
     *
     * @param roomNumber    room number
     * @param pricePerNight price per night
     * @param roomType      room type
     */
    public void addRoom(String roomNumber, double pricePerNight, int roomType) {
        RoomType rt;
        if (roomType == 1) {
            rt = RoomType.SINGLE;
        } else {
            rt = RoomType.DOUBLE;
        }

        Room room = new Room(roomNumber, pricePerNight, rt);
        ReservationService.getInstance().addRoom(room);
    }

    /**
     * Gets all rooms
     *
     * @return Collection of rooms
     */
    public Collection<IRoom> getAllRooms() {
        return ReservationService.getInstance().getAllRooms().values();
    }

    /**
     * Gets all customers
     *
     * @return Collections of customers
     */
    public Collection<Customer> getAllCustomers() {
        return CustomerService.getInstance().getAllCustomers().values();
    }

    /**
     * Gets all reservations
     *
     * @return Collection of reservations
     */
    public Collection<Reservation> getAllReservations() {
        List<Reservation> allReservations = new ArrayList<>();
        Collection<List<Reservation>> reservations = ReservationService.getInstance().getAllReservation().values();
        for (List<Reservation> listRes : reservations) {
            allReservations.addAll(listRes);
        }
        return allReservations;
    }

}
