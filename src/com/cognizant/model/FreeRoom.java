package com.cognizant.model;

/**
 * Free room class
 */
public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0d, roomType);
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public String toString() {
        return "com.cognizant.model.FreeRoom{} " + super.toString();
    }

}
