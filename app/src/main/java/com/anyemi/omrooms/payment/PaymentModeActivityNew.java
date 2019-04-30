package com.anyemi.omrooms.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anyemi.omrooms.Model.ServicesResponseModel;
import com.anyemi.omrooms.R;
import com.anyemi.omrooms.Utils.SharedPreferenceConfig;
import com.anyemi.omrooms.payment.bgtask.BackgroundTask;
import com.anyemi.omrooms.payment.bgtask.BackgroundThread;
import com.anyemi.omrooms.payment.connection.HomeServices;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.ArrayList;


public class PaymentModeActivityNew extends AppCompatActivity {

    private static final String TAG_PAYMENTMODE = PaymentModeActivityNew.class.getName();
    //Action Bar
    TextView aTitle, notification_count;
    RelativeLayout rl_new_mails;
    ImageView iv_add_new;
    Toolbar toolbar;
    GridView gv_services;
    ArrayList<ServicesResponseModel.FinancerBean.PaymentModeBean> ary_data = new ArrayList<>();
    PaymentModesAdapter mAdapter;
    ApplicationData applicationData;
    ServicesResponseModel servicesResponseModel;

    String paymentData;
    PaymentRequestModel paymentRequestModel;
    TextView tv_amount;

    Intent resultIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_modes);

        resultIntent = getIntent();
/*


 */
        SharedPreferenceUtil.setFIN_ID(getApplicationContext(), "2");  // Pass Finacier Id Her
        SharedPreferenceUtil.setUserId(getApplicationContext(), "11");//"a".concat(new SharedPreferenceConfig(this).readPhoneNo()));  //Pass User Id Here

        applicationData = (ApplicationData) getApplication();

      getData();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(PaymentModeActivityNew.this, R.color.colorPrimaryDark));
        }

        paymentRequestModel = Globals.getPaymentRequestModes(getIntent().getExtras());
        Log.e(TAG_PAYMENTMODE,"data at "+new Gson().toJson(paymentRequestModel));

        if (paymentRequestModel == null) {
            Globals.showToast(getApplicationContext(), Constants.PAYMENT_REQ_ERROR);
            finish();
        }

       // createActionBar();
        initView();
    }


    private void getData() {

        new BackgroundTask(PaymentModeActivityNew.this, new BackgroundThread() {
            @Override
            public Object runTask() {
                return HomeServices.getCollections(PaymentModeActivityNew.this);
            }

            public void taskCompleted(Object data) {

                if (data != null && !data.toString().equals("")) {

                    try {
                        servicesResponseModel = new ServicesResponseModel();
                        servicesResponseModel = new Gson().fromJson(data.toString(), ServicesResponseModel.class);
                        applicationData.setServicesResponseModel(servicesResponseModel);

                        parseData();

//                        servicesResponseModel = new ServicesResponseModel();
//                        servicesResponseModel = applicationData.getServicesResponseModel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Globals.showToast(getApplicationContext(), "No Data Found");
                }
            }
        }, getString(R.string.loading_txt)).execute();
    }


    private void initView() {

        tv_amount = (TextView) findViewById(R.id.tv_amount);


        String resultStr = String.valueOf(paymentRequestModel.getTotal_amount());
        resultStr = Utils.parseAmount(resultStr);

        tv_amount.setText("Rs." + resultStr + " /-");


        gv_services = (GridView) findViewById(R.id.gv_services);
        mAdapter = new PaymentModesAdapter(getApplicationContext(), ary_data);
        gv_services.setAdapter(mAdapter);


        gv_services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /*
                 *
                 * Payment ID
                 * FIN_ID
                 */
                paymentRequestModel.setPayment_type(ary_data.get(i).getPaymentmode_code());
                paymentRequestModel.setFIN_ID(SharedPreferenceUtil.getFIN_ID(getApplicationContext()));
                paymentRequestModel.setUser_id(Integer.parseInt(SharedPreferenceUtil.getUserId(getApplicationContext())));
                getSelectedPayment(ary_data.get(i).getPaymentmode_code());

            }
        });
    }


    public void getSelectedPayment(String payment_mode) {


        Intent paymentIntent;

        if (payment_mode.equals(Constants.PAYMENT_MODE_PAYTM_SBI_UPI)) {
            paymentIntent = new Intent(getApplicationContext(), SbiPayPaymentActivity.class);
            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
            Log.e("payment activity new",""+new Gson().toJson(paymentRequestModel));
            startActivityForResult(paymentIntent,5);
        } else if (payment_mode.equals(Constants.PAYMENT_MODE_CASH)) {
            paymentIntent = new Intent(getApplicationContext(), CompleateTransactionActivity.class);
            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
            startActivity(paymentIntent);
        } else if (payment_mode.equals(Constants.PAYMENT_MODE_ANYEMI_WALLET)) {
            paymentIntent = new Intent(getApplicationContext(), CompleateTransactionActivity.class);
            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
            startActivity(paymentIntent);
        } else {
            paymentIntent = new Intent(getApplicationContext(), CompleateTransactionActivity.class);
            paymentIntent.putExtra(Constants.PAYMENT_REQUEST_MODEL, new Gson().toJson(paymentRequestModel));
            startActivity(paymentIntent);
        }


    }


    private void parseData() {


        ary_data.clear();
        String group_id = SharedPreferenceUtil.getGroupId(getApplicationContext());
        String FIN_ID = SharedPreferenceUtil.getFIN_ID(getApplicationContext());
        //   String FIN_ID = "2";


        for (ServicesResponseModel.FinancerBean financerBean : servicesResponseModel.getFinancer()) {

            if (financerBean.getId().equals(FIN_ID)) {

                for (ServicesResponseModel.FinancerBean.PaymentModeBean paymentModeBean : financerBean.getPayment_mode()) {

                    if (paymentModeBean.getIs_public().equals("0")) {
                        if (group_id.equals(Constants.LOGIN_TYPE_AGENT)) {
                            ary_data.add(paymentModeBean);
                        }
                    } else {
                        ary_data.add(paymentModeBean);
                    }

                }

                break;


            }

        }


        mAdapter.notifyDataSetChanged();
    }


    private void createActionBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar_with_home_button, null);

        aTitle = (TextView) mCustomView.findViewById(R.id.title_text);
        rl_new_mails = (RelativeLayout) mCustomView.findViewById(R.id.rl_new_mails);
        notification_count = (TextView) mCustomView.findViewById(R.id.text_count);
        iv_add_new = (ImageView) mCustomView.findViewById(R.id.iv_add_new);

        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        aTitle.setTextColor(getResources().getColor(R.color.white));
        aTitle.setText("Choose payment Method");
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


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


    public class PaymentModesAdapter extends BaseAdapter {

        private Context c;
        private LayoutInflater mInflater;
        ViewHolder _listView;
        ArrayList<ServicesResponseModel.FinancerBean.PaymentModeBean> tenant_matches_listings;

        public PaymentModesAdapter(Context c, ArrayList<ServicesResponseModel.FinancerBean.PaymentModeBean> mListings) {
            this.tenant_matches_listings = mListings;
            this.c = c;
            mInflater = LayoutInflater.from(c);
        }


        @Override
        public int getCount() {
            return tenant_matches_listings.size();
        }

        @Override
        public Object getItem(int position) {
            return tenant_matches_listings.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // final MyAccountListingsResponse2 studentList = tenant_matches_listings.get(position);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.lv_tile_event, null, false);
                _listView = new ViewHolder();
                _listView.tv_shop_name = (TextView) convertView.findViewById(R.id.tv_shop_name);
                _listView.iv_item = (ImageView) convertView.findViewById(R.id.iv_item);


                convertView.setTag(_listView);
            } else {
                _listView = (ViewHolder) convertView.getTag();
            }

            try {

                _listView.tv_shop_name.setText(tenant_matches_listings.get(position).getPayment_mode());

                try {
                    String url = tenant_matches_listings.get(position).getPaymentmode_icon().toString();
                    Glide.with(getApplicationContext())
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            // .centerCrop()
                            .error(R.mipmap.ic_launcher)
                            .into(_listView.iv_item);
                } catch (Exception e) {
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        class ViewHolder {
            TextView tv_shop_name;
            ImageView iv_item;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 5 && resultCode == RESULT_OK){

            String transactionId = null;
            String status = null;
            if(data!= null){



                transactionId = data.getStringExtra("transactionId");
                status = data.getStringExtra("status");
                resultIntent.putExtra("transactionId",transactionId);
                resultIntent.putExtra("status",status);

                setResult(Activity.RESULT_OK,resultIntent);
                finish();

                Log.e(TAG_PAYMENTMODE,"aaaaa "+transactionId+status);

            }

        }

        Log.e("payment Mode new",""+requestCode+resultCode);
    }
}