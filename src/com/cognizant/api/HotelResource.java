package com.cognizant.api;

import com.cognizant.model.Customer;
import com.cognizant.model.IRoom;
import com.cognizant.model.Pair;
import com.cognizant.model.Reservation;
import com.cognizant.service.CustomerService;
import com.cognizant.service.ReservationService;

import java.util.*;

/**
 * Hotel Resource class
 */
public class HotelResource {

    private static final HotelResource SINGLETON = new HotelResource();

    private HotelResource() {
    }

    /**
     * Gets instance of Hotel resource
     *
     * @return SINGLETON
     */
    public static HotelResource getInstance() {
        return SINGLETON;
    }

    /**
     * Gets Customer
     *
     * @param email email of customer
     * @return customer object
     */
    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    /**
     * Creates customer
     *
     * @param firstName first name of customer
     * @param lastName  last name of customer
     * @param email     email of customer
     */
    public void createCustomer(String firstName, String lastName, String email) {
        try {
            Customer customer = new Customer(firstName, lastName, email);
            CustomerService.getInstance().addCustomer(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Email for customer is not valid, please create account with valid email");
        }
    }

    /**
     * Gets room
     *
     * @param roomNumber room number
     * @return IRoom object
     */
    public IRoom getRoom(String roomNumber) {
        return ReservationService.getInstance().getRoom(roomNumber);
    }

    /**
     * Checking if there are available rooms are exist
     *
     * @param roomNumber     room number
     * @param availableRooms collection of available rooms
     * @return boolean value
     */
    public boolean isRoomExistInAvailable(String roomNumber, Collection<IRoom> availableRooms) {
        for (IRoom availableRoom : availableRooms) {
            if (roomNumber.equals(availableRoom.getRoomNumber())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Books room
     *
     * @param customerEmail customer email
     * @param roomID        room ID
     * @param checkInDate   check in date
     * @param checkOutDate  check out date
     * @return reservation
     */
    public Reservation bookRoom(String customerEmail, String roomID, Date checkInDate, Date checkOutDate) {
        Customer customer = AdminResource.getInstance().getCustomer(customerEmail);

        IRoom room = HotelResource.getInstance().getRoom(roomID);

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        room.getBookedDates().add(Pair.createPair(checkInDate, checkOutDate));
        ReservationService.getInstance().reserveRoom(reservation);
        return reservation;
    }

    /**
     * Finds available rooms
     *
     * @param checkIn  check in
     * @param checkOut check out
     * @return available rooms
     */
    public Collection<IRoom> findAvailableRooms(Date checkIn, Date checkOut) {
        List<IRoom> availableRooms = new ArrayList<>();
        Collection<IRoom> allRooms = ReservationService.getInstance().getAllRooms().values();

        for (IRoom room : allRooms) {
            if (room.getBookedDates().size() == 0) {
                availableRooms.add(room);
                continue;
            }

            boolean isSuitable = false;
            for (Pair<Date, Date> bookedDates : room.getBookedDates()) {
                if (!isCheckInCheckOutInBookedRange(checkIn, checkOut, bookedDates) && !isCheckInCheckOutOutsideBookedRange(checkIn, checkOut, bookedDates)) {
                    isSuitable = true;
                } else {
                    isSuitable = false;
                    break;
                }
            }
            if (isSuitable) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    /**
     * Checking if there are check in and check out dates in booked range
     *
     * @param checkIn     check in
     * @param checkOut    check out
     * @param bookedDates booked dates
     * @return boolean value
     */
    private boolean isCheckInCheckOutInBookedRange(Date checkIn, Date checkOut, Pair<Date, Date> bookedDates) {
        if ((checkIn.equals(bookedDates.getBookedChekIn()) || checkIn.after(bookedDates.getBookedChekIn()))
                && (checkIn.equals(bookedDates.getBookedCheckOut()) || checkIn.before(bookedDates.getBookedCheckOut()))) {
            return true;
        }
        return (checkOut.equals(bookedDates.getBookedChekIn()) || checkOut.after(bookedDates.getBookedChekIn()))
                && (checkOut.equals(bookedDates.getBookedCheckOut()) || checkOut.before(bookedDates.getBookedCheckOut()));
    }

    /**
     * Checking if there are check in and check out dates outside of booked range
     *
     * @param checkIn     check in
     * @param checkOut    check out
     * @param bookedDates booked dates
     * @return boolean value
     */
    private boolean isCheckInCheckOutOutsideBookedRange(Date checkIn, Date checkOut, Pair<Date, Date> bookedDates) {
        return checkIn.before(bookedDates.getBookedChekIn()) && checkOut.after(bookedDates.getBookedCheckOut());
    }

    /**
     * Gets customer reservation
     *
     * @param email
     * @return Collection of customer reservation
     */
    public Collection<Reservation> getCustomerReservation(String email) {
        return ReservationService.getInstance().getCustomerReservation(email);
    }

    public Date addDefaultDays(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 7);
        return c.getTime();
    }
}
