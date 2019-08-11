package org.sairaa.omowner.Pricing;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;

import org.sairaa.omowner.Pricing.Model.PriceResponse;
import org.sairaa.omowner.Pricing.Model.RoomTypeAndPrice;

import java.util.ArrayList;
import java.util.List;

public class PricingRepository {
    private static final String TAG_PRICING = PricingRepository.class.getName();
    private static PricingRepository instance;
    private MutableLiveData<PriceResponse> roomPrice = new MutableLiveData<>();
    private List<RoomTypeAndPrice> roomPriceList = new ArrayList<>();

    public static PricingRepository getInstance(){
        if(instance == null){
            instance = new PricingRepository();
        }
        return instance;
    }

    LiveData<PriceResponse> getRoomPriceAndType(){
        return roomPrice;
    }

    void setRoomPrice(PriceResponse priceResponse){
        roomPrice.setValue(priceResponse);
    }

    void saveRoomPriceAndType(RoomTypeAndPrice roomPrice) {
        if(roomPriceList.size()>0 && roomPrice != null){
            RoomTypeAndPrice roomP = null;
            for(RoomTypeAndPrice roomTypeAndPrice: roomPriceList){
                if(roomTypeAndPrice.getRoom_type().equals(roomPrice.getRoom_type())){
                    roomTypeAndPrice = roomPrice;
                    roomP = roomPrice;
                }
            }
            if(roomP == null){
                roomPriceList.add(roomPrice);
            }
        }else {
            roomPriceList.add(roomPrice);
        }

        Log.e(TAG_PRICING,""+new Gson().toJson(roomPriceList));
    }

    void setDefaultRoomPrice(){
        roomPriceList.clear();

    }

    List<RoomTypeAndPrice> getRoomPriceList(){
        return roomPriceList;
    }
}
