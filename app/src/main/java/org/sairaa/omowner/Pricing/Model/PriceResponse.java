package org.sairaa.omowner.Pricing.Model;

import java.util.List;

public class PriceResponse {
    private String status;
    private String msg;
    private List<RoomTypeAndPrice> getRoomPriceWithType;

    public PriceResponse() {
    }

    public PriceResponse(String status, String msg, List<RoomTypeAndPrice> getRoomPriceWithType) {
        this.status = status;
        this.msg = msg;
        this.getRoomPriceWithType = getRoomPriceWithType;
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

    public List<RoomTypeAndPrice> getGetRoomPriceWithType() {
        return getRoomPriceWithType;
    }

    public void setGetRoomPriceWithType(List<RoomTypeAndPrice> getRoomPriceWithType) {
        this.getRoomPriceWithType = getRoomPriceWithType;
    }
}
