package com.example.retrofit2.network;

import com.example.retrofit2.model.Feeds;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("photos_public.gne")
    Call<Feeds> getItems(@Query("format") String type,
                         @Query("nojsoncallback") int no );
}
