package org.sairaa.omowner.CheckIn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.sairaa.omowner.R;

public class CheckInForm extends Activity {

    Button button,checkinsubmit,checkincancel;
    EditText etbookid,etname,etnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in_form);

        button  = (Button) findViewById(R.id.btn_picker);
        checkinsubmit = findViewById(R.id.checkinsubmit);
        checkincancel = findViewById(R.id.checkincancel);

        etbookid = findViewById(R.id.et_bookid);
        etname = findViewById(R.id.et_name);
        etnumber = findViewById(R.id.et_number);

        checkinsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etbookid.getText().toString().equals(""))
                {
                    if(!etname.getText().toString().equals(""))
                    {
                        if(!etnumber.getText().toString().equals("")){

                            if( etnumber.getText().toString().length() == 10 ){

                                Intent it = new Intent(CheckInForm.this, CheckInActivity.class);
                                startActivity(it);

                            }
                            else
                            {
                                Toast.makeText(CheckInForm.this, "Phone number must be 10 digits....", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(CheckInForm.this, "Phone number Can't be Empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(CheckInForm.this, "Name can't be empty ", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CheckInForm.this, "booking Id can't be empty ", Toast.LENGTH_SHORT).show();
                }


            }
        });

        checkincancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 7);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    String PathHolder = data.getData().getPath();
                    Toast.makeText(CheckInForm.this, PathHolder, Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}