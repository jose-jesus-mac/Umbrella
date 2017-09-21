package com.android.umbrella;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.android.umbrella.databinding.ActivityMainBinding;
import com.android.umbrella.databinding.ContentMainBinding;
import com.android.umbrella.databinding.WeathercardBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    String[] time = {
            "1:00 P.M.",
            "2:00 P.M.",
            "3:00 P.M.",
            "4:00 P.M.",
            "5:00 P.M.",
            "6:00 P.M.",
            "7:00 P.M.",
            "8:00 P.M."

    } ;
    int[] imageId = {
            R.drawable.weather_cloudy,
            R.drawable.weather_fog,
            R.drawable.weather_hail,
            R.drawable.weather_lightning_rainy,
            R.drawable.weather_partlycloudy,
            R.drawable.weather_rainy,
            R.drawable.weather_snowy,
            R.drawable.weather_snowy_rainy

    };
    String[] temp = {
            "71*",
            "72*",
            "73*",
            "74*",
            "75*",
            "76*",
            "77*",
            "78*"

    } ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

      // ContentMainBinding Cbinding = DataBindingUtil.setContentView(this, R.layout.content_main);
       // WeathercardBinding weathercardBinding = DataBindingUtil.setContentView(this, R.layout.weathercard);

        GridViewAdapter adapter = new GridViewAdapter(MainActivity.this, time, imageId, temp);
        binding.contentmain.card1.gridview.setAdapter(adapter);
        binding.contentmain.card2.gridview.setAdapter(adapter);

      //  pDialog = new ProgressDialog(this);
      //  pDialog.setMessage("Loading...");
      //  pDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Making json object request
     * *
    private void makeJsonObjReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                R.string.URLcall, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        response.getJSONObject()
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * *
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Androidhive");
                params.put("email", "abc@androidhive.info");
                params.put("pass", "password123");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
            */
}
