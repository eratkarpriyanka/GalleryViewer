package com.getaround.getaroundgalleryviewer.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndpointInterface {

    String BASE_URL = "https://api.500px.com/v1/";

    @GET("photos")
    Call<ResponseGetPhoto> getPhotosApi(@Query("feature") String feature, @Query("only") String category);
}
