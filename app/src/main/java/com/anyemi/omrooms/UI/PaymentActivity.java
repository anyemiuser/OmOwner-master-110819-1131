package com.anyemi.omrooms.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anyemi.omrooms.Model.PaymentRequestModel;
import com.anyemi.omrooms.R;
import com.google.gson.Gson;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView price;
    private Button success, failure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        String model = intent.getStringExtra("payment");
        Gson gson = new Gson();
        PaymentRequestModel paymentRequestModel = gson.fromJson(model, PaymentRequestModel.class);
        Log.e("test",""+new Gson().toJson(paymentRequestModel));
        price = findViewById(R.id.price_text);
        success = findViewById(R.id.success);
        success.setOnClickListener(this);
        failure = findViewById(R.id.failure);
        failure.setOnClickListener(this);

        price.setText(paymentRequestModel.getTotal_amount());
    }

    @Override
    public void onClick(View view) {
        Intent resultIntent = getIntent();
        switch (view.getId()){
            case R.id.success:

                resultIntent.putExtra("transactionId","1234525");
                resultIntent.putExtra("status","s");
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
                break;
            case R.id.failure:

                resultIntent.putExtra("transactionId","125442");
                resultIntent.putExtra("status","f");
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
                break;
        }
    }
}
