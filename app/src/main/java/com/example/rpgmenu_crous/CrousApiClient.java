package com.example.rpgmenu_crous;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CrousApiClient {
    @GET("regions/{regionId}/restaurants")
    Call<List<Restaurant>> getRestaurantsByRegion(@Path("regionId") int regionId);
}
