package com.cognizant.model;

import java.util.Date;
import java.util.Objects;

/**
 * Reservation class
 */
public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    /**
     * Gets reservation
     *
     * @param customer     customer
     * @param room         room
     * @param checkInDate  check in date
     * @param checkOutDate check out date
     */
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Reservation() {
    }

    /**
     * Gets customer
     *
     * @return customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets customer
     *
     * @param customer Customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets room
     *
     * @return room
     */
    public IRoom getRoom() {
        return room;
    }

    /**
     * Sets room
     *
     * @param room Iroom room
     */
    public void setRoom(IRoom room) {
        this.room = room;
    }

    /**
     * Gets check in date
     *
     * @return check in date
     */
    public Date getCheckInDate() {
        return checkInDate;
    }

    /**
     * Sets check in date
     *
     * @param checkInDate check in date
     */
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    /**
     * Gets check out date
     *
     * @return check out date
     */
    public Date getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Sets check out date
     *
     * @param checkOutDate check out date
     */
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation: " + "\n" +
                "Customer: " + customer + "," + "\n" +
                "Room: " + room + "," + "\n" +
                "CheckIn Date: " + checkInDate + "," + "\n" +
                "CheckOut Date: " + checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return customer.equals(that.customer) && room.equals(that.room) && checkInDate.equals(that.checkInDate) && checkOutDate.equals(that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
