package com.anyemi.omrooms.Model;

import java.io.Serializable;

public class RoomsGuest implements Serializable {

    private int rooms;
    private int guests;

    public RoomsGuest() {
    }

    public RoomsGuest(int rooms, int guests) {
        this.rooms = rooms;
        this.guests = guests;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }
}
