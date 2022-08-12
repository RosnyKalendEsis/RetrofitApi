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

public class MainActivity extends AppCompatActivity {
    private TextView textViewLogin;
    private TextView textViewName;
    private TextView textViewId;
    private EditText editTextId;
    private Button buttonSoumettre;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        setEcouteurEvenement();
    }
    private void initialization(){
        textViewName=findViewById(R.id.textViewName);
        textViewLogin=findViewById(R.id.textViewLogin);
        textViewId=findViewById(R.id.textViewId);
        editTextId=findViewById(R.id.editTextId);
        buttonSoumettre=findViewById(R.id.buttonSoumettre);
        progressBar=findViewById(R.id.progressBar);
    }
    private void setEcouteurEvenement(){
        buttonSoumettre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idText=editTextId.getText().toString();
                if(idText.isEmpty()){
                    Toast.makeText(MainActivity.this,"L'id ne peut pas etre vide", Toast.LENGTH_LONG).show();
                }else{
                    int id=Integer.parseInt(idText);
                    recupererGithubUser(id);
                }
            }
        });
    }

    private void recupererGithubUser(int id) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GithubUserService userService=retrofit.create(GithubUserService.class);
        Call<GithubUsers> callback=userService.getUser(id);
        callback.enqueue(new Callback<GithubUsers>() {
            @Override
            public void onResponse(Call<GithubUsers> call, Response<GithubUsers> response) {
                if(response.isSuccessful()){
                    GithubUsers user= response.body();
                    if (user==null){
                        Toast.makeText(MainActivity.this,"user not found",Toast.LENGTH_LONG);
                    }else{
                        textViewLogin.setText("Login: "+user.getLogin());
                        textViewName.setText("Name: "+user.getName());
                        textViewId.setText("Id: "+user.getId());
                    }

                }else{
                    Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_LONG).show();
                }
                   progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<GithubUsers> call, Throwable t) {
                    Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG);
            }
        });
    }
}