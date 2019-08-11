package org.sairaa.omowner.Pricing.Model;

import java.util.List;

public class RoomTypeList {
    private String status;
    private String msg;
    private List<RoomType> getRoomType;

    public RoomTypeList() {
    }

    public RoomTypeList(String status, String msg, List<RoomType> getRoomType) {
        this.status = status;
        this.msg = msg;
        this.getRoomType = getRoomType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RoomType> getGetRoomType() {
        return getRoomType;
    }

    public void setGetRoomType(List<RoomType> getRoomType) {
        this.getRoomType = getRoomType;
    }
}
