package com.rom.quizup.client.services;

import com.rom.quizup.client.models.Player;
import com.rom.quizup.client.models.PlayerCollection;
import com.rom.quizup.client.models.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gal on 31/03/2016.
 */
public interface PlayerServices {

    @POST("players/register")
    Call<Player> registerUser(@Header("gToken") String token);

    @GET("players/list")
    Call<List<Player>> getPlayerList(@Header("gToken") String token);

    @GET("players/me")
    Call<Player> getPlayer(@Header("gToken") String token);

    @POST("players/device/{deviceId}/{deviceType}")
    Call<Map<String, Boolean>> registerDevice(@Path("deviceId") String deviceId, @Path("deviceType") Integer deviceType, @Header("gToken") String token);

    @POST("players/device/{deviceId}")
    Call<Map<String, Boolean>> unregisterDevice(@Path("deviceId") String deviceId, @Header("gToken") String token);
}
