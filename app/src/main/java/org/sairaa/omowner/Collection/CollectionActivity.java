package org.sairaa.omowner.Collection;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.instamojo.android.Instamojo;

import org.json.JSONException;
import org.json.JSONObject;
import org.sairaa.omowner.R;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectionActivity extends AppCompatActivity implements Instamojo.InstamojoPaymentCallback{


    private static final HashMap<Instamojo.Environment, String> env_options = new HashMap<>();
    private static final String TAG_COLLECTION = CollectionActivity.class.getName();

    static {
        env_options.put(Instamojo.Environment.TEST, "https://test.instamojo.com/");
        env_options.put(Instamojo.Environment.PRODUCTION, "https://api.instamojo.com/");
    }

    private Instamojo.Environment mCurrentEnv = Instamojo.Environment.TEST;
    private boolean mCustomUIFlow = false;

    private MyBackendService myBackendService;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Payment");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Instamojo.getInstance().initialize(this, mCurrentEnv);

        // Initialize the backend service client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sample-sdk-server.instamojo.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myBackendService = retrofit.create(MyBackendService.class);

        createOrderOnServer();

//        Instamojo.getInstance().initiatePayment(this, "as123451", this);
        // Call the function callInstamojo to start payment hereInstamojo.getInstance().initialize(this, Instamojo.Environment.TEST);
//        callInstamojoPay("prafulnayk1988@gmail.com","9666235167","12","pay","praful nayak");
    }

    private void createOrderOnServer() {
        GetOrderIDRequest request = new GetOrderIDRequest();
        request.setEnv(mCurrentEnv.name());
        request.setBuyerName("Prafulla");
        request.setBuyerEmail("prafulnayak1988@gmail.com");
        request.setBuyerPhone("9666235167");
        request.setDescription("test instamojo");
        request.setAmount("15");

        Call<GetOrderIDResponse> getOrderIDCall = myBackendService.createOrder(request);

        getOrderIDCall.enqueue(new retrofit2.Callback<GetOrderIDResponse>() {
            @Override
            public void onResponse(Call<GetOrderIDResponse> call, Response<GetOrderIDResponse> response) {
                if (response.isSuccessful()) {
                    String orderId = response.body().getOrderID();

//                    if (!mCustomUIFlow) {
                        // Initiate the default SDK-provided payment activity


//                    } else {
//                        // OR initiate a custom UI activity
//                        initiateCustomPayment(orderId);
//                    }
                    Log.e(TAG_COLLECTION,"order Id: "+orderId);
                    initiateSDKPayment(orderId);
                } else {
                    // Handle api errors
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.d(TAG_COLLECTION, "Error in response" + jObjError.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetOrderIDResponse> call, Throwable t) {
                // Handle call failure
                Log.d(TAG_COLLECTION, "Failure");
            }
        });
    }

    private void initiateSDKPayment(String orderId) {
        Instamojo.getInstance().initiatePayment(this, orderId, this);
    }

    @Override
    public void onInstamojoPaymentComplete(String s, String s1, String s2, String s3) {

        Log.d(TAG_COLLECTION, "Success"+s+"  : "+s1+" : "+s3);

    }

    @Override
    public void onPaymentCancelled() {
        Log.d(TAG_COLLECTION, "Cancelled");
    }

    @Override
    public void onInitiatePaymentFailure(String s) {

        Log.d(TAG_COLLECTION, "init fail: "+s);

    }
}
