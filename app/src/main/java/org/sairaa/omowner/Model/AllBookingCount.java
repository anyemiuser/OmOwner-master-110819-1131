package org.sairaa.omowner.Model;

import java.util.List;

public class AllBookingCount {
    private String bookingType;
    private int noOfBookings;
    private int noOfRoomBooked;
    private int totalRooms;
    private List<RoomTypeCount> roomTypeCountList;

    public AllBookingCount() {
    }

    public AllBookingCount(String bookingType, int noOfBookings, int noOfRoomBooked, int totalRooms, List<RoomTypeCount> roomTypeCountList) {
        this.bookingType = bookingType;
        this.noOfBookings = noOfBookings;
        this.noOfRoomBooked = noOfRoomBooked;
        this.totalRooms = totalRooms;
        this.roomTypeCountList = roomTypeCountList;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public int getNoOfBookings() {
        return noOfBookings;
    }

    public void setNoOfBookings(int noOfBookings) {
        this.noOfBookings = noOfBookings;
    }

    public int getNoOfRoomBooked() {
        return noOfRoomBooked;
    }

    public void setNoOfRoomBooked(int noOfRoomBooked) {
        this.noOfRoomBooked = noOfRoomBooked;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    public List<RoomTypeCount> getRoomTypeCountList() {
        return roomTypeCountList;
    }

    public void setRoomTypeCountList(List<RoomTypeCount> roomTypeCountList) {
        this.roomTypeCountList = roomTypeCountList;
    }
}
