package org.sairaa.omowner.HomeRules;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import org.sairaa.omowner.Utils.SharedPreferenceConfig;

import java.util.ArrayList;
import java.util.List;


public class HomeRulesActivity extends AppCompatActivity {


    private static final String URL_DATA = "https://www.omrooms.com/webservices/omrooms/Owner/api.php?f=hotelrules&hotel_id=";
    private static final String URL_DATA1 = "https://www.omrooms.com/webservices/omrooms/Owner/api.php?f=getnumrules&hotel_id=";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<RulesListItem> listitems;
    private SharedPreferenceConfig sharedPreferenceConfig;
    Switch r1,r2,r3,r4,r5,r6;
    ToggleButton tb1,tb2,tb3,tb4,tb5,tb6;
   public static String str1,str2,str3,str4,str5,str6;
  TextView t1,t2,t3,t4,t5,t6;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_rules);


        sharedPreferenceConfig = new SharedPreferenceConfig(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null) {
            actionBar.setTitle("Hotel Rules");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        t6 = findViewById(R.id.t6);




        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
        r5 = findViewById(R.id.r5);
        r6 = findViewById(R.id.r6);




        listitems = new ArrayList<>();

        loadRecyclerViewData();
        getstates();

     //   state();








    }

    public void getstates()
    {
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET,
                URL_DATA1 +sharedPreferenceConfig.readHotelId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("getnumrules");

                            for(int i = 0; i<jsonArray1.length(); i++)
                            {
                                JSONObject o1 = jsonArray1.getJSONObject(i);
                              //  RulesListItem item = new RulesListItem(
                              str1 = o1.getString("nononveg");
                                str2=o1.getString("nopak");
                                str3 =    o1.getString("nosmoking");
                               str4=    o1.getString("nooreignguest");
                               str5=    o1.getString("notripleoccupancy");
                                str6=   o1.getString("noalcohol");

                               // Log.d("res1", o1.getString("nononveg"));
                              //  Log.d("res", item.toString());
                               // listitems.add(item);
                            }


                           /* adapter = new RulesListAdapter(listitems,getApplicationContext());
                            recyclerView.setAdapter(adapter);*/

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
        requestQueue.add(stringRequest1);


    }











    public void loadRecyclerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA+sharedPreferenceConfig.readHotelId(),
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



    public void state()
    {
        if(str1.equals("0")){
            r1.setChecked(true);
        }
    }
}
