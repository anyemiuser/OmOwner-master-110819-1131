package org.sairaa.omowner.instamojo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.instamojo.android.Instamojo;

import org.sairaa.omowner.Api.ApiUtils;
import org.sairaa.omowner.Api.OmRoomApi;
import org.sairaa.omowner.CheckIn.CheckInActivity;
import org.sairaa.omowner.Model.PaymentResponseModel;
import org.sairaa.omowner.R;
import org.sairaa.omowner.instamojo.adapter.PaymentModesAdapter;
import org.sairaa.omowner.instamojo.model.InstamojoPaymentModel;
import org.sairaa.omowner.instamojo.model.IstamojoModel;
import org.sairaa.omowner.instamojo.model.PaymentRequestModel;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.instamojo.android.helpers.Constants.PAYMENT_ID;
import static org.sairaa.omowner.instamojo.Constants.PAYMENT_REQ_ERROR;

public class InstamojoActivity extends AppCompatActivity implements Instamojo.InstamojoPaymentCallback {

    private static final String TAG = InstamojoActivity.class.getSimpleName();
    private static final HashMap<Instamojo.Environment, String> env_options = new HashMap<>();

    ArrayList<InstamojoPaymentModel.InstamojoPaymentModesBean> ary_data = new ArrayList<>();

    static {
        env_options.put(Instamojo.Environment.TEST, "https://test.instamojo.com/");
        env_options.put(Instamojo.Environment.PRODUCTION, "https://api.instamojo.com/");
    }


    //Action Bar
    TextView aTitle, notification_count;
    RelativeLayout rl_new_mails;
    ImageView iv_add_new;
    Toolbar toolbar;

    private AlertDialog dialog;

    private Instamojo.Environment mCurrentEnv = Instamojo.Environment.PRODUCTION;
    TextView tv_amount;
    ListView lv_services;
    PaymentModesAdapter mAdapter;

    private MyBackendService myBackendService;
    InstamojoPaymentModel instamojoPaymentModel;
    PaymentRequestModel paymentRequestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instamojo);

        mCurrentEnv = Instamojo.Environment.PRODUCTION;

       Instamojo.getInstance().initialize(InstamojoActivity.this, mCurrentEnv);

        initView();
        //createActionBar();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(InstamojoActivity.this, R.color.colorPrimaryDark));
        }

        getInstaPModes();
    }


    private void initView() {

        paymentRequestModel = Globals.getPaymentRequestModes(getIntent().getExtras());

        tv_amount = (TextView) findViewById(R.id.tv_amount);


        String resultStr = String.valueOf(paymentRequestModel.getTotal_amount());
        // resultStr = Utils.parseAmount(resultStr);

        tv_amount.setText("Rs." + resultStr + " /-");


        //gv_services = (GridView) findViewById(R.id.gv_services);
        lv_services = (ListView) findViewById(R.id.gv_services);
        mAdapter = new PaymentModesAdapter(getApplicationContext(), ary_data);
        lv_services.setAdapter(mAdapter);

//        customBrowserConfig.setPayuPostData(payuConfig.getData());

        lv_services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (ary_data.get(i).getIs_status().equals("1")) {
                    generateHashKey(ary_data.get(i).getKey());
                } else {
                    Globals.showToast(getApplicationContext(), "We Will Update Soon");
                }
            }
        });
    }


    private void generateHashKey(final String type) {

        GetOrderIDRequest getOrderIDRequest = prepRq(type);


        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        //  UserTypeRequest user = new UserTypeRequest(phoneNumber);
        //  Log.e(LOGIN_TAG,""+new Gson().toJson(user));
        // omRoomApi.generateOrderId().enqueue(new Callback<getOrderIDRequest>() {
        omRoomApi.generateOrderId(getOrderIDRequest).enqueue(new Callback<IstamojoModel>() {
            @Override
            public void onResponse(Call<IstamojoModel> call, Response<IstamojoModel> response) {

                if (response.isSuccessful()) {
                    IstamojoModel oeder_id_model = response.body();
                    //Log.e(LOGIN_TAG,""+new Gson().toJson(userTypeResponse));

                    if (oeder_id_model != null) {
                        if (oeder_id_model.getStatus().equals("success")) {


                            initiateSDKPayment(oeder_id_model.getOrder_id());


                            // initiateCustomPayment(checkSumModel.getOrder_id());
                        } else {
                            Globals.showToast(getApplicationContext(), "Unable to generate hash");
                        }


                    } else {

                        Toast.makeText(getApplicationContext(), oeder_id_model.getMsg(), Toast.LENGTH_LONG);
                    }


                }

            }

            @Override
            public void onFailure(Call<IstamojoModel> call, Throwable t) {
                // Log.e(LOGIN_TAG,""+t.toString());
                Toast.makeText(InstamojoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getInstaPModes() {


        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        //  UserTypeRequest user = new UserTypeRequest(phoneNumber);
        //  Log.e(LOGIN_TAG,""+new Gson().toJson(user));
        omRoomApi.getInstaMojoPaymentModes("instpmodes_servtax").enqueue(new Callback<InstamojoPaymentModel>() {
            @Override
            public void onResponse(Call<InstamojoPaymentModel> call, Response<InstamojoPaymentModel> response) {

                if (response.isSuccessful()) {
                    instamojoPaymentModel = response.body();
                    //Log.e(LOGIN_TAG,""+new Gson().toJson(userTypeResponse));

                    if (instamojoPaymentModel != null) {
                        if (instamojoPaymentModel.getStatus().equals("Success")) {

                            ary_data.addAll((ArrayList<InstamojoPaymentModel.InstamojoPaymentModesBean>)
                                    instamojoPaymentModel.getInstamojo_payment_modes());

                            mAdapter.notifyDataSetChanged();

                        } else {

                            Toast.makeText(getApplicationContext(), instamojoPaymentModel.getMsg(), Toast.LENGTH_LONG);
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<InstamojoPaymentModel> call, Throwable t) {
                // Log.e(LOGIN_TAG,""+t.toString());
                Toast.makeText(InstamojoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private GetOrderIDRequest prepRq(String type) {
        GetOrderIDRequest request = new GetOrderIDRequest();
        try {
            paymentRequestModel = Globals.getPaymentRequestModes(getIntent().getExtras());

            if (paymentRequestModel == null) {
                Globals.showToast(getApplicationContext(), PAYMENT_REQ_ERROR);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // String amount = paymentRequestModel.getTotal_amount();

        paymentRequestModel = Globals.getInstaAmount(
                getApplicationContext(),
                paymentRequestModel,
                instamojoPaymentModel,
                type);
        String name = "";
        String email = "";
        String moile = "";


        if (email.equals("")) {
            email = "helpdesk@anyemi.com";
        }

        if (name.equals("")) {
            name = "anyemi";
        }

        if (moile == null || moile.equals("")) {
            moile = "8008615500";
        }

        String amount = paymentRequestModel.getTotal_amount();
        request.setEnv(mCurrentEnv.name());
        request.setBuyerName(name);
        request.setBuyerEmail(email);
        request.setBuyerPhone(moile);
        request.setDescription("payment");
        request.setAmount(amount);
        // request.setAmount("10");
        request.setType(type);

        return request;
    }


    private void initiateSDKPayment(String orderID) {
        Instamojo.getInstance().initiatePayment(this, orderID, InstamojoActivity.this);
    }


    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Will check for the transaction status of a particular Transaction
     *
     * @param transactionID Unique identifier of a transaction ID
     */
    private void checkPaymentStatus(final String transactionID, final String orderID) {
        if (transactionID == null && orderID == null) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("Checking transaction status");
        Call<GatewayOrderStatus> getOrderStatusCall = myBackendService.orderStatus(mCurrentEnv.name().toLowerCase(),
                orderID, transactionID);
        getOrderStatusCall.enqueue(new Callback<GatewayOrderStatus>() {
            @Override
            public void onResponse(Call<GatewayOrderStatus> call, final Response<GatewayOrderStatus> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    GatewayOrderStatus orderStatus = response.body();
                    if (orderStatus.getStatus().equalsIgnoreCase("successful")) {
                        showToast("Transaction still pending");
                        return;
                    }

                    showToast("Transaction successful for id - " + orderStatus.getPaymentID());
                    refundTheAmount(transactionID, orderStatus.getAmount());

                } else {
                    showToast("Error occurred while fetching transaction status");
                }
            }

            @Override
            public void onFailure(Call<GatewayOrderStatus> call, Throwable t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        showToast("Failed to fetch the transaction status");
                    }
                });
            }
        });
    }


    private void refundTheAmount(String transactionID, String amount) {
        if (transactionID == null || amount == null) {
            return;
        }

        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }

        showToast("Initiating a refund for - " + amount);
        Call<ResponseBody> refundCall = myBackendService.refundAmount(
                mCurrentEnv.name().toLowerCase(),
                transactionID, amount);
        refundCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (response.isSuccessful()) {
                    showToast("Refund initiated successfully");

                } else {
                    showToast("Failed to initiate a refund");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                showToast("Failed to Initiate a refund");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == com.instamojo.android.helpers.Constants.REQUEST_CODE && data != null) {
            String orderID = data.getStringExtra(com.instamojo.android.helpers.Constants.ORDER_ID);
            String transactionID = data.getStringExtra(com.instamojo.android.helpers.Constants.TRANSACTION_ID);
            String paymentID = data.getStringExtra(PAYMENT_ID);

            // Check transactionID, orderID, and orderID for null before using them to check the Payment status.
            if (transactionID != null || paymentID != null) {
                checkPaymentStatus(transactionID, orderID);
            } else {
                showToast("Oops!! Payment was cancelled");
            }
        }
    }


    private void submitPayment() {

        Globals.showToast(getApplicationContext(), "Payment Successful");


        //GetOrderIDRequest getOrderIDRequest = prepRq(type);


        OmRoomApi omRoomApi = ApiUtils.getOmRoomApi();
        //  UserTypeRequest user = new UserTypeRequest(phoneNumber);
        //  Log.e(LOGIN_TAG,""+new Gson().toJson(user));
        // omRoomApi.generateOrderId().enqueue(new Callback<getOrderIDRequest>() {
        omRoomApi.postPay(paymentRequestModel).enqueue(new Callback<PaymentResponseModel>() {
            @Override
            public void onResponse(Call<PaymentResponseModel> call, Response<PaymentResponseModel> response) {

                if (response.isSuccessful()) {
                    PaymentResponseModel paymentResponseModel = response.body();
                    //Log.e(LOGIN_TAG,""+new Gson().toJson(userTypeResponse));

                    if (paymentResponseModel != null) {
                        if (paymentResponseModel.getStatus().equals("Success")) {

                            Intent it = new Intent(InstamojoActivity.this, CheckInActivity.class);
                            startActivity(it);

                            // initiateCustomPayment(checkSumModel.getOrder_id());
                        } else {
                            Globals.showToast(getApplicationContext(), "Unable to generate hash");
                        }


                    } else {

                        Toast.makeText(getApplicationContext(), paymentResponseModel.getMsg(), Toast.LENGTH_LONG);
                    }


                }

            }

            @Override
            public void onFailure(Call<PaymentResponseModel> call, Throwable t) {
                // Log.e(LOGIN_TAG,""+t.toString());
                Toast.makeText(InstamojoActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onInstamojoPaymentComplete(String orderID, String transactionID, String paymentID, String paymentStatus) {
        Log.d(TAG, "Payment complete");
//        showToast("Payment complete. Order ID: " + orderID + ", Transaction ID: " + transactionID
//                + ", Payment ID:" + paymentID + ", Status: " + paymentStatus);

        if (paymentStatus.equalsIgnoreCase("Credit")) {
            paymentRequestModel.setTrsno(orderID);
            paymentRequestModel.setRr_number(paymentID);
            submitPayment();
        }

    }

    @Override
    public void onPaymentCancelled() {
        Log.d(TAG, "Payment cancelled");
        showToast("Payment cancelled by user");
    }

    @Override
    public void onInitiatePaymentFailure(String errorMessage) {
        Log.d(TAG, "Initiate payment failed");
        showToast("Initiating payment failed. Error: " + errorMessage);
    }

//    private void createActionBar() {
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("");
//
//        LayoutInflater mInflater = LayoutInflater.from(this);
//        View mCustomView = mInflater.inflate(R.layout.custom_action_bar_with_home_button, null);
//
//        aTitle = (TextView) mCustomView.findViewById(R.id.title_text);
//        rl_new_mails = (RelativeLayout) mCustomView.findViewById(R.id.rl_new_mails);
//        notification_count = (TextView) mCustomView.findViewById(R.id.text_count);
//        iv_add_new = (ImageView) mCustomView.findViewById(R.id.iv_add_new);
//
//        getSupportActionBar().setCustomView(mCustomView);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        aTitle.setTextColor(getResources().getColor(R.color.white));
//        aTitle.setText("Choose payment Method");
//    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
