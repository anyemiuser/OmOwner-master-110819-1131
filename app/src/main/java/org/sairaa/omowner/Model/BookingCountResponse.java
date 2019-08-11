package org.sairaa.omowner.Model;

import java.util.List;

public class BookingCountResponse {

    private String status;
    private String message;
    private List<TotalRooms> noofrooms;
    private List<RoomTarrif> tarrif;
    private UpcomingCountList upcomingList;
    private InhouseCountList inhouseList;
    private CompletedCountList completedlist;

    public BookingCountResponse() {
    }

    public BookingCountResponse(String status, String message, List<TotalRooms> noofrooms, List<RoomTarrif> tarrif, UpcomingCountList upcomingList, InhouseCountList inhouseList, CompletedCountList completedlist) {
        this.status = status;
        this.message = message;
        this.noofrooms = noofrooms;
        this.tarrif = tarrif;
        this.upcomingList = upcomingList;
        this.inhouseList = inhouseList;
        this.completedlist = completedlist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TotalRooms> getNoofrooms() {
        return noofrooms;
    }

    public void setNoofrooms(List<TotalRooms> noofrooms) {
        this.noofrooms = noofrooms;
    }

    public List<RoomTarrif> getTarrif() {
        return tarrif;
    }

    public void setTarrif(List<RoomTarrif> tarrif) {
        this.tarrif = tarrif;
    }

    public UpcomingCountList getUpcomingList() {
        return upcomingList;
    }

    public void setUpcomingList(UpcomingCountList upcomingList) {
        this.upcomingList = upcomingList;
    }

    public InhouseCountList getInhouseList() {
        return inhouseList;
    }

    public void setInhouseList(InhouseCountList inhouseList) {
        this.inhouseList = inhouseList;
    }

    public CompletedCountList getCompletedlist() {
        return completedlist;
    }

    public void setCompletedlist(CompletedCountList completedlist) {
        this.completedlist = completedlist;
    }
}
