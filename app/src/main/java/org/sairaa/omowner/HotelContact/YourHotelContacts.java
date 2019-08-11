package org.sairaa.omowner.HotelContact;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sairaa.omowner.R;

import java.util.ArrayList;
import java.util.List;

public class YourHotelContacts extends AppCompatActivity {

    private static final String URL_DATA = "https://dev.anyemi.com/webservices/omrooms/Owner/api.php?f=hotelcontacts&hotel_id=905";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ContactListItem> listitems;



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_hotel_contacts);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setTitle("Hotel Contacts");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listitems = new ArrayList<>();

        loadRecyclerViewData();



    }

    public void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("hotelcontacts");

                            for(int i = 0; i<jsonArray.length(); i++)
                            {
                                JSONObject o = jsonArray.getJSONObject(i);
                                ContactListItem item = new ContactListItem(
                                        o.getString("hotel_manger_no"),
                                        o.getString("hotel_manger_email"),
                                        o.getString("hotel_receptionist_no"),
                                        o.getString("hotel_receptionist_mail")

                                );
                                listitems.add(item);
                            }
                            adapter = new ContactAdapter(listitems,getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
