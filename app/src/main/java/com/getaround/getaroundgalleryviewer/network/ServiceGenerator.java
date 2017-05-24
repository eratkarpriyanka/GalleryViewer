package com.getaround.getaroundgalleryviewer.network;


import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    public static final String TAG = ServiceGenerator.class.getSimpleName();

    // Trailing slash is needed
    public static final String API_BASE_URL = "https://api.500px.com/v1/";

    public static ApiEndpointInterface createService() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

              //  String credentials = Credentials.basic("UU6XQeziu01adhSANZo3J5gDsZD6gaFyJXomYlhz", "5CZl9ZYnOwvOn35Jg3sKFFDR6nKZC4DK2NctRPUc");
              //  Log.e(TAG," credentials "+credentials);

                HttpUrl originalHttpUrl = originalRequest.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("consumer_key", "UU6XQeziu01adhSANZo3J5gDsZD6gaFyJXomYlhz").build();

                Request.Builder builder = originalRequest.newBuilder().url(url);

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndpointInterface apiService = retrofit.create(ApiEndpointInterface.class);
        return apiService;
    }
}