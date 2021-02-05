package com.ezcommerce.client;

import com.ezcommerce.model.ResponseJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Route {
    @GET("book")
    Call<ResponseJSON> getProducts(@Query("nim") String nim, @Query("nama") String name);

    @GET("book/{id}")
    Call<ResponseJSON> getProduct(@Path(value = "id") int id, @Query("nim") String nim,
                                  @Query("nama") String name);
}
