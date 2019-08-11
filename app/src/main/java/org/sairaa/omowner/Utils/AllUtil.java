package org.sairaa.omowner.Utils;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.google.gson.Gson;

import org.sairaa.omowner.Model.RoomCheck;

import java.util.List;

public class AllUtil {



    public static boolean compareList(List<RoomCheck> roomCheckList1,List<RoomCheck> roomCheckList2){
        boolean isEqual = false;
        for(RoomCheck check2: roomCheckList2){

            for(RoomCheck check1: roomCheckList1){
                if(check1.getRoomType().equals(check2.getRoomType()) && check1.getNoOfRoom() == check2.getNoOfRoom()){
                    isEqual = true;
                }

            }

        }

        return isEqual;
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
