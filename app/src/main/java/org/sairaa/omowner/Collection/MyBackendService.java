package org.sairaa.omowner.Collection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyBackendService {



    @Headers({
            "X-Api-Key: test_fe46cc3f71a001f4c49b",
            "X-Auth-Token: test_5a21b8caed69daca11943",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @POST("/order")
    Call<GetOrderIDResponse> createOrder(@Body GetOrderIDRequest request);


    @Headers({
            "X-Api-Key: test_fe46cc3f71a001f4c49b",
            "X-Auth-Token: test_5a21b8caed69dac20b113",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @GET("/status")
    Call<GatewayOrderStatus> orderStatus(@Query("env") String env, @Query("order_id") String orderID,
                                         @Query("transaction_id") String transactionID);


    @Headers({
            "X-Api-Key: test_fe46cc3f71a001f4c49b",
            "X-Auth-Token: test_5a21b8caed69dac211943",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @POST("/refund")
    Call<ResponseBody> refundAmount(@Query("env") String env,
                                    @Query("transaction_id") String transaction_id,
                                    @Query("amount") String amount);
}
