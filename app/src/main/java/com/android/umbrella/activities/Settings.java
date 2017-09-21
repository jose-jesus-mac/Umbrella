package com.android.umbrella.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.umbrella.MainActivity;
import com.android.umbrella.R;
import com.android.umbrella.databinding.SettingsBinding;


public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] units={"fahrenheit", "celsius"};
    String zip;
    static int grades =0;
    SettingsBinding settingsBinding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       settingsBinding= DataBindingUtil.setContentView(this, R.layout.settings);
        Spinner spinner = settingsBinding.units;

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,units);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(arrayAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        grades = position;
    }

    @Override
    public void onBackPressed()
    {
        String content = settingsBinding.zip.getText().toString();
        if(content.length()==5) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putExtra("zip", content);
            intent.putExtra("units", String.valueOf(grades));
            startActivity(intent);
        }else{
            Toast.makeText(Settings.this, "please insert a valid zip code!",
                    Toast.LENGTH_LONG).show();
        }
        super.onBackPressed();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
