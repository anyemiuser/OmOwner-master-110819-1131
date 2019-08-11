package org.sairaa.omowner.HomeRules;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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


public class HomeRulesActivity extends AppCompatActivity {


    private static final String URL_DATA = "https://dev.anyemi.com/webservices/omrooms/Owner/api.php?f=hotelrules&hotel_id=905";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<RulesListItem> listitems;


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_rules);




        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setTitle("Home Rules");
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
                            JSONArray jsonArray = jsonObject.getJSONArray("hotelrules");

                            for(int i = 0; i<jsonArray.length(); i++)
                            {
                                JSONObject o = jsonArray.getJSONObject(i);
                                RulesListItem item = new RulesListItem(
                                        o.getString("rule1"),
                                        o.getString("rule2"),
                                        o.getString("rule3"),
                                        o.getString("rule4"),
                                        o.getString("rule5"),
                                        o.getString("rule6")

                                );
                                Log.d("res", item.toString());
                                listitems.add(item);
                            }
                            adapter = new RulesListAdapter(listitems,getApplicationContext());
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
