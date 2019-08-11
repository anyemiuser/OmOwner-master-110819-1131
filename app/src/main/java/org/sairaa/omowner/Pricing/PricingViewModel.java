package org.sairaa.omowner.Pricing;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.Pricing.Model.PriceRequest;
import org.sairaa.omowner.Pricing.Model.PriceResponse;
import org.sairaa.omowner.Pricing.Model.RoomTypeAndPrice;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PricingViewModel extends AndroidViewModel {

    private PricingRepository repository;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> loadingText = new MutableLiveData<>();

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

    public PricingViewModel(@NonNull Application application) {
        super(application);
        repository = PricingRepository.getInstance();
    }



    LiveData<PriceResponse> getRoomPriceAndType(){
        return repository.getRoomPriceAndType();
    }

    public void onRetrieveRoomPriceOnDate(PriceRequest priceRequest){
        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        isLoading.setValue(true);
        loadingText.setValue("Loading...");
        omRoomApi.getRoomPriceOnDate(priceRequest).enqueue(new Callback<PriceResponse>() {
            @Override
            public void onResponse(Call<PriceResponse> call, Response<PriceResponse> response) {
                isLoading.setValue(false);
                if(response.isSuccessful()){
                    PriceResponse priceResponse = response.body();
                    if (priceResponse != null && priceResponse.getStatus().equals("Success") &&
                            priceResponse.getMsg().equals("Success")) {
                        loadingText.setValue("Room Types");
                        repository.setRoomPrice(priceResponse);
                    }else {
                        loadingText.setValue("Price Not available on this date");
                    }
                }else {
                    loadingText.setValue(""+response.message());
                }
            }

            @Override
            public void onFailure(Call<PriceResponse> call, Throwable t) {
                loadingText.setValue(t.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    void saveRoomPriceAndType(RoomTypeAndPrice roomPrice) {
        repository.saveRoomPriceAndType(roomPrice);
    }

    void setDefaultRoomPrice(){
        repository.setDefaultRoomPrice();
    }

    List<RoomTypeAndPrice> getRoomPriceList(){
        return repository.getRoomPriceList();
    }
}
