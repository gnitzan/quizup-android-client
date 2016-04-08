package com.rom.quizup.client.services;

import android.util.Log;

import com.rom.quizup.client.GameBackendSettings;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A singleton service that wraps Retrofit our http rest API
 */
public enum Quizup {
    INSTANCE;

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private PlayerServices playerServices;
    private GameServices gameServices;


    Quizup() {
        okHttpClient  = new OkHttpClient.Builder()
                .readTimeout(6000, TimeUnit.SECONDS)
                .connectTimeout(6000, TimeUnit.SECONDS)
                /*.addInterceptor(new NetLoggingInterceptor())*/
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(GameBackendSettings.DEFAULT_ROOT_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        playerServices = retrofit.create(PlayerServices.class);
        gameServices = retrofit.create(GameServices.class);
    }

    public Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        Log.w("Retrofit@Response", response.body().string());
        return response;
    }

    public PlayerServices getPlayerServices() {
        return playerServices;
    }

    public GameServices getGameServices() {
        return gameServices;
    }
}
