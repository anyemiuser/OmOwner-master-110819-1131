package com.anyemi.omrooms.Model;

import java.io.Serializable;

public class RoomsGuest implements Serializable {

    private int rooms;
    private int guests;
    private int children;

    public RoomsGuest() {
    }

    public RoomsGuest(int rooms, int guests) {
        this.rooms = rooms;
        this.guests = guests;
    }

    public RoomsGuest(int rooms, int guests, int children) {
        this.rooms = rooms;
        this.guests = guests;
        this.children = children;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
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
