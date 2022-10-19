package com.cognizant.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Room class
 */
public class Room implements IRoom {
    private String roomNumber;
    private Double price;
    private RoomType roomType;
    private List<Pair<Date, Date>> bookedDates = new ArrayList<>();

    /**
     * Gets room
     *
     * @param roomNumber room number
     * @param price      price
     * @param roomType   room type
     */
    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public List<Pair<Date, Date>> getBookedDates() {
        return bookedDates;
    }

    @Override
    public String toString() {
        return "" +
                "Room Number: " + roomNumber +
                ", Room Price: " + price +
                ", Room Type: " + roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}



    
