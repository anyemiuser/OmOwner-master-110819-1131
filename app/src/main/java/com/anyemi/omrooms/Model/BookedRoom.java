package com.anyemi.omrooms.Model;

class BookedRoom {
    private String noofroomsbooked;
    private String room_type;

    public BookedRoom() {
    }

    public BookedRoom(String noofroomsbooked, String room_type) {
        this.noofroomsbooked = noofroomsbooked;
        this.room_type = room_type;
    }

    public String getNoofroomsbooked() {
        return noofroomsbooked;
    }

    public void setNoofroomsbooked(String noofroomsbooked) {
        this.noofroomsbooked = noofroomsbooked;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }
}
