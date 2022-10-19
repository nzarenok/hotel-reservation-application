package com.cognizant.model;

public class Pair<K, V> {

    private final K bookedChekIn;
    private final V bookedCheckOut;

    public static <K, V> Pair<K, V> createPair(K bookedChekIn, V bookedCheckOut) {
        return new Pair<K, V>(bookedChekIn, bookedCheckOut);
    }

    /**
     * Creates pair of objects
     *
     * @param bookedChekIn   bookedChekIn
     * @param bookedCheckOut bookedCheckOut
     */
    public Pair(K bookedChekIn, V bookedCheckOut) {
        this.bookedChekIn = bookedChekIn;
        this.bookedCheckOut = bookedCheckOut;
    }

    /**
     * Gets booked check in
     *
     * @return booked chek in
     */
    public K getBookedChekIn() {
        return bookedChekIn;
    }

    /**
     * Gets booked check out
     *
     * @return booked check out
     */
    public V getBookedCheckOut() {
        return bookedCheckOut;
    }

}
