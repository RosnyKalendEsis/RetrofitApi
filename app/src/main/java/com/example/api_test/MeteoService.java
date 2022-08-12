package com.example.api_test;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MeteoService {
    @GET("/bin/astro.php")
    Call<Meteo> getMeteo(
            @Query("lon") Double lon,
            @Query("lat") Double lat,
            @Query("ac") int ac,
            @Query("unit") String unit,
            @Query("output") String output,
            @Query("tzshift") int tzshift
    );
}
