package com.cognizant.model;

import java.util.Date;
import java.util.List;

/**
 * IRoom interface
 */
public interface IRoom {
    String getRoomNumber();

    Double getRoomPrice();

    RoomType getRoomType();

    /**
     * Returns boolean free flag
     *
     * @return boolean free flag
     */
    boolean isFree();

    List<Pair<Date, Date>> getBookedDates();
}
