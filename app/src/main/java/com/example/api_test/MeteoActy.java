package com.example.api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeteoActy extends AppCompatActivity {
    private TextView temperature;
    private TextView transparence;
    private EditText longitude;
    private EditText latitude;
    private EditText acceleration;
    private EditText unit;
    private EditText output;
    private EditText timeZoneShift;
    private Button buttonsoumettre;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo_acty);
        initialization();
        setEcouteurEvenement();
    }
    private void initialization(){
        temperature=findViewById(R.id.textViewTemperature);
        transparence=findViewById(R.id.textViewTransparence);
        longitude=findViewById(R.id.editTextLon);
        latitude=findViewById(R.id.editTextLat);
        acceleration=findViewById(R.id.editTextAc);
        unit=findViewById(R.id.editTextUnit);
        output=findViewById(R.id.editTextOutput);
        timeZoneShift=findViewById(R.id.editTextTzshift);
        progressBar=findViewById(R.id.progressBar);
        buttonsoumettre=findViewById(R.id.buttonSubmit);
    }
    private void setEcouteurEvenement(){
        buttonsoumettre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lonText=longitude.getText().toString();
                String latText=latitude.getText().toString();
                String acText=acceleration.getText().toString();
                String unitText=unit.getText().toString();
                String outputText=output.getText().toString();
                String tzText=timeZoneShift.getText().toString();
                if(lonText.isEmpty()){
                    Toast.makeText(MeteoActy.this,"La longitude ne peut pas etre vide", Toast.LENGTH_LONG).show();
                }else if(latText.isEmpty()){
                    Toast.makeText(MeteoActy.this,"La latitude ne peut pas etre vide", Toast.LENGTH_LONG).show();
                }else if(acText.isEmpty()){
                    Toast.makeText(MeteoActy.this,"La acceleration ne peut pas etre vide", Toast.LENGTH_LONG).show();
                }else if(unitText.isEmpty()){
                    Toast.makeText(MeteoActy.this,"La unit ne peut pas etre vide", Toast.LENGTH_LONG).show();
                }else if(outputText.isEmpty()){
                    Toast.makeText(MeteoActy.this,"L'output ne peut pas etre vide", Toast.LENGTH_LONG).show();
                }else if(tzText.isEmpty()){
                    Toast.makeText(MeteoActy.this,"La time zone shift ne peut pas etre vide", Toast.LENGTH_LONG).show();
                }
                else{
                    Double lonParam=Double.parseDouble(lonText);
                    Double latParam=Double.parseDouble(latText);
                    int acParam=Integer.parseInt(acText);
                    int tzParam=Integer.parseInt(tzText);
                    recupererMeteo(lonParam,latParam,acParam,unitText,outputText,tzParam);
                }
            }
        });
    }
    private void recupererMeteo(Double lon, Double lat, int ac, String unit,String output,int tzshift){
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://www.7timer.info")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MeteoService meteoService=retrofit.create(MeteoService.class);
        Call<Meteo> callback=meteoService.getMeteo(lon, lat, ac, unit, output, tzshift);
        callback.enqueue(new Callback<Meteo>() {
            @Override
            public void onResponse(Call<Meteo> call, Response<Meteo> response) {
                if(response.isSuccessful()){
                    Meteo meteo= response.body();
                    if (meteo==null){
                        Toast.makeText(MeteoActy.this,"meteo not found",Toast.LENGTH_LONG);
                    }else{
                        temperature.setText("Temperature: "+meteo.getDataSeries().get(0).getTemp2m());
                        transparence.setText("Transparence: "+meteo.getDataSeries().get(0).getTransparency());
                    }

                }else{
                    Toast.makeText(MeteoActy.this,"Error", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Meteo> call, Throwable t) {
                Toast.makeText(MeteoActy.this,t.getMessage(),Toast.LENGTH_LONG);
            }
        });

    }
}