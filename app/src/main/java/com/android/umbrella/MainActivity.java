package com.android.umbrella;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.umbrella.adapter.GridViewAdapter;
import com.android.umbrella.activities.Settings;
import com.android.umbrella.databinding.ActivityMainBinding;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public ProgressDialog pDialog;
    static String s;
    static int h=0,j=0,k=0,l=0;
    String location;
    static String sw;
    public String TAG = MainActivity.class.getSimpleName();
    public JSONArray json_1;
    public JSONObject json_2;
    String[] timed= new String[24];
    String[] timed2=new String[24];
    int[] imageIds=new int[24];
    int[] imageIds2=new int[24];
    static int count=0;
    static int cold=100;
    static int hot=0;
    static int cold2=100;
    static int hot2=0;
    int temps[]=new int[24];
    int temps2[]=new int[24];
    ActivityMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
       setSupportActionBar(binding.toolbar);

        int actionBarColor = getResources().getColor(R.color.weather_cool);
        binding.toolbar.setBackgroundDrawable(new ColorDrawable(actionBarColor));
        binding.appBar.setBackgroundDrawable(new ColorDrawable(actionBarColor));


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(true);
        s = getIntent().getStringExtra("units");
        sw = getIntent().getStringExtra("zip");
       makeJsonObjReq(sw,s);
    }

    @Override
    public void onResume(){
        super.onResume();
        s = getIntent().getStringExtra("units");
        sw = getIntent().getStringExtra("zip");
        makeJsonObjReq(sw,s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,
                    Settings.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Making json object request
     * */
    private void makeJsonObjReq(String zip, final String Units) {
        if (zip != null) {
        showProgressDialog();
        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                "http://api.wunderground.com/api/5b4be481d919b50c/conditions/hourly/q/"+zip+".json", null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        response.toString();
                        try {
                            json_2 = response.getJSONObject("current_observation").getJSONObject("observation_location");
                            json_1 = response.getJSONArray("hourly_forecast");
                            location = json_2.getString("full");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < json_1.length(); i++) {
                            try {
                                JSONObject json_data = json_1.getJSONObject(i);
                                JSONObject FCTTIME = json_1.getJSONObject(i).getJSONObject("FCTTIME");
                                int mday = FCTTIME.getInt("mday");
                                Calendar calander = Calendar.getInstance();
                                int cDay = calander.get(Calendar.DAY_OF_MONTH);
                                if (mday == cDay) {
                                    timed[i] = FCTTIME.getString("civil");
                                    JSONObject temp = json_1.getJSONObject(i).getJSONObject("temp");
                                    if(Integer.parseInt(Units)==0){

                                        temps[i] = temp.getInt("english");
                                        if(i==0){
                                            binding.toolbarLayout.setTitle(String.valueOf(temps[i]+"\u00B0"));
                                            binding.location.setText(location);
                                            if(temps[i]<60){
                                                binding.toolbarLayout.setBackgroundDrawable((new ColorDrawable(getResources().getColor(R.color.weather_cool))));
                                                binding.appBar.setBackgroundDrawable(new ColorDrawable((getResources().getColor(R.color.weather_cool))));
                                                binding.toolbar.setBackgroundDrawable(new ColorDrawable((getResources().getColor(R.color.weather_cool))));
                                            }else {
                                                binding.toolbarLayout.setBackgroundDrawable((new ColorDrawable(getResources().getColor(R.color.weather_warm))));
                                                binding.appBar.setBackgroundDrawable(new ColorDrawable((getResources().getColor(R.color.weather_warm))));
                                                binding.toolbar.setBackgroundDrawable(new ColorDrawable((getResources().getColor(R.color.weather_warm))));
                                            }
                                        }
                                    }else {
                                        temps[i] = temp.getInt("metric");
                                        if(i==0){
                                            binding.toolbarLayout.setTitle(String.valueOf(temps[i])+"\u00B0");
                                        } if(temps[i]>15){
                                            binding.toolbarLayout.setBackgroundDrawable((new ColorDrawable(getResources().getColor(R.color.weather_cool))));
                                            binding.appBar.setBackgroundDrawable(new ColorDrawable((getResources().getColor(R.color.weather_cool))));
                                        }else {
                                            binding.toolbarLayout.setBackgroundDrawable((new ColorDrawable(getResources().getColor(R.color.weather_warm))));
                                            binding.appBar.setBackgroundDrawable(new ColorDrawable((getResources().getColor(R.color.weather_warm))));
                                        }
                                    }

                                    if(temps[i]<cold){
                                        cold=temps[i];
                                        h=i;
                                    }
                                    if(temps[i]>hot){
                                        hot=temps[i];
                                        j=i;
                                    }
                                    String condition = json_1.getJSONObject(i).getString("condition");
                                    binding.condition.setText(condition);
                                    imageIds[i] = condition(condition);


                                } else if (mday == cDay + 1) {
                                    timed2[count] = FCTTIME.getString("civil");
                                    JSONObject temp = json_1.getJSONObject(i).getJSONObject("temp");
                                    if(Integer.parseInt(Units)==0){
                                        temps2[count] = temp.getInt("english");
                                    }else {
                                        temps2[count] = temp.getInt("metric");
                                    }
                                    String condition = json_1.getJSONObject(i).getString("condition");
                                    imageIds2[count] = condition(condition);
                                    if(temps2[count]<cold2){
                                        cold2=temps2[count];
                                        k=count;
                                    }
                                    if(temps2[count]>hot2){
                                        hot2=temps2[count];
                                        l=count;
                                    }
                                    count++;
                                } else {
                                    break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Log.d(TAG, response.toString());
                        GridViewAdapter adapter = new GridViewAdapter(MainActivity.this, timed, imageIds, temps,j, h);
                        binding.contentmain.card1.gridview.setAdapter(adapter);
                        GridViewAdapter adapter2 = new GridViewAdapter(MainActivity.this, timed2, imageIds2, temps2, l, k);
                        binding.contentmain.card2.day.setText("Tomorrow");
                        binding.contentmain.card2.gridview.setAdapter(adapter2);
                        count=0;

                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, "didn't got response!",
                        Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
        rq.add(jsonObjReq);
        }else {
            Toast.makeText(getApplicationContext(), "Please Insert Zip",
                    Toast.LENGTH_LONG).show();
        }
    }

    public int condition(String condition){
        int t = 0;
        switch(condition){
            case "Partly Cloudy":t = R.drawable.weather_partlycloudy; break;
            case "Mostly Cloudy":t = R.drawable.weather_cloudy; break;
            case "Clear":t = R.drawable.weather_sunny; break;
            case "Overcast":t = R.drawable.weather_fog; break;
            case "Chance of Rain":t = R.drawable.weather_rainy; break;
            case "Rain":t = R.drawable.weather_rainy; break;
            case "Chance of a Thunderstorm": t = R.drawable.weather_lightning; break;
            case "Thunderstorm": t = R.drawable.weather_lightning_rainy; break;
            case "Chance of Hail":t = R.drawable.weather_hail; break;
            case "Hail":t = R.drawable.weather_hail; break;
            case "Chance of Snow": t = R.drawable.weather_snowy; break;
            case "Snow": t = R.drawable.weather_snowy_rainy; break;
            case "Windy": t = R.drawable.weather_windy_variant; break;
            default:
                Log.e("", "no case");
        }
        return t;
    }

}
