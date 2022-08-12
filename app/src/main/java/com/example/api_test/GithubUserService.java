package com.example.api_test;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubUserService {
    @GET("/users/{id}")
    Call<GithubUsers> getUser(@Path("id") int id);
}
