package org.sairaa.omowner.RoomUtility;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.RoomUtility.Model.UtilitiyDetails;
import org.sairaa.omowner.RoomUtility.Model.UtilityRequest;
import org.sairaa.omowner.RoomUtility.Model.UtilityResponse;
import org.sairaa.omowner.RoomUtility.Model.UtilityUpdateRequest;
import org.sairaa.omowner.RoomUtility.Model.UtilityUpdateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilityViewModel extends AndroidViewModel {

    private UtilityRepository repository;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> loadingText = new MutableLiveData<>();

    public UtilityViewModel(@NonNull Application application) {
        super(application);
        repository = UtilityRepository.getInstance();
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean value) {
        isLoading.setValue(value);
    }

    public LiveData<String> getLoadingText() {
        return loadingText;
    }

    public void setLoadingText(String loadingTextD) {
        loadingText.setValue(loadingTextD);
    }


    public void retrieveUtilityDetails(UtilityRequest utilityRequest) {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        isLoading.setValue(true);
        loadingText.setValue("Loading...");
        omRoomApi.getUtilityDetails(utilityRequest).enqueue(new Callback<UtilityResponse>() {
            @Override
            public void onResponse(Call<UtilityResponse> call, Response<UtilityResponse> response) {
                isLoading.setValue(false);
                if(response.isSuccessful()){
                    UtilityResponse utilityResponse = response.body();
                    if(utilityResponse != null){
                        if(utilityResponse.getStatus().equals("Success") && utilityResponse.getMsg().equals("Success")){
                            repository.setUtilityDetails(utilityResponse.getGetUtilityDetails());
                            loadingText.setValue("Utility Details");
                        }else {
                            loadingText.setValue(utilityResponse.getMsg());

                        }
                    }else {
                        loadingText.setValue("Something Went Wrong");
                    }
                }else {
                    loadingText.setValue("Error: "+response.body());
                }
            }

            @Override
            public void onFailure(Call<UtilityResponse> call, Throwable t) {
                isLoading.setValue(false);
                loadingText.setValue(""+t.getMessage());

            }
        });
    }

    LiveData<List<UtilitiyDetails>> getUtilityDetails(){
        return repository.getUtilityDetails();
    }

    public void updateUtility(String hotelId) {
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        UtilityUpdateRequest utilityUpdateRequest = new UtilityUpdateRequest(hotelId,repository.getUtilityDetails().getValue());
        isLoading.setValue(true);
        loadingText.setValue("Updating Utils...");
        omRoomApi.updateUtils(utilityUpdateRequest).enqueue(new Callback<UtilityUpdateResponse>() {
            @Override
            public void onResponse(Call<UtilityUpdateResponse> call, Response<UtilityUpdateResponse> response) {
                isLoading.setValue(false);
                if(response.isSuccessful()){
                    UtilityUpdateResponse updateResponse = response.body();
                    if(updateResponse != null){
                        if(updateResponse.getStatus().equals("success")&& updateResponse.getMessage().equals("success update")){
                            loadingText.setValue("Updated Successfully");
                        }else {
                            loadingText.setValue(""+updateResponse.getMessage());
                        }
                    }else {
                        loadingText.setValue(""+response.message());
                    }

                }else {
                    loadingText.setValue(""+response.message());
                }
            }

            @Override
            public void onFailure(Call<UtilityUpdateResponse> call, Throwable t) {

            }
        });
    }
}
