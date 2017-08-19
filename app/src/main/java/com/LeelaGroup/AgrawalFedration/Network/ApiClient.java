package com.LeelaGroup.AgrawalFedration.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hp on 02-Jun-17.
 */

public class ApiClient {

    public static final String BASE_URL = "http://sanket21.host22.com";
    private static Retrofit retrofit = null;

        public static Retrofit getRetrofit() {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            return retrofit;
    }
}
