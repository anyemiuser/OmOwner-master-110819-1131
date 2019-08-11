package org.sairaa.omowner.RoomUtility;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import org.sairaa.omowner.RoomUtility.Model.UtilitiyDetails;

import java.util.List;

public class UtilityRepository {
    private MutableLiveData<List<UtilitiyDetails>> utilityList = new MutableLiveData<>();
    private static UtilityRepository instance;
    public static UtilityRepository getInstance() {
        if(instance == null){
            instance = new UtilityRepository();
        }
        return instance;
    }

    public void setUtilityDetails(List<UtilitiyDetails> getUtilityDetails) {
        utilityList.setValue(getUtilityDetails);
    }

    LiveData<List<UtilitiyDetails>> getUtilityDetails(){
        return utilityList;
    }
}
