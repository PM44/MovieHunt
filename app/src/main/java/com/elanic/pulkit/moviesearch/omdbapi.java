package com.elanic.pulkit.moviesearch;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pulkit on 26/3/17.
 */

public interface omdbapi {
    String baseurl = "http://www.omdbapi.com";

    @GET("/")
    Call<Movies> getInfo(@Query("s") String s, @Query("type") String type, @Query("page") int page);

    class Factory {
        public static omdbapi services = null;

        public static omdbapi getInstance() {
            if (services == null) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseurl).build();
                services = retrofit.create(omdbapi.class);
                return services;
            } else {
                return services;
            }
        }
    }
}
