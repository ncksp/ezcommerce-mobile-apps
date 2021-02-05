package com.ezcommerce.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/staging/";

    public static Retrofit getInstance(){
        if (retrofit != null)
            return retrofit;

        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
