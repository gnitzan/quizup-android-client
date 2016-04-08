package com.rom.quizup.client.services;

import com.rom.quizup.client.models.Game;
import com.rom.quizup.client.models.GamePlayStatus;
import com.rom.quizup.client.models.QuInvitation;
import com.rom.quizup.client.models.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gal on 31/03/2016.
 */
public interface GameServices {

    @GET("games/{gameId}")
    Call<Game> getGame(@Path("gameId") String gameId, @Header("gToken") String token);

    @POST("games/startMulti/{level}/{inviteeId}")
    Call<QuInvitation> startMultiPlayerGame(@Path("inviteeId") String inviteeId, @Path("level") Integer boardLevel, @Header("gToken") String token);

    @POST("games/startSingle/{level}")
    Call<Game> startSinglePlayerGame(@Path("level") Integer boardLevel, @Body User player);

    @POST("games/{gameId}/invitation/{invitationId}/accept")
    Call<Map<String, Boolean>> acceptInvitation(@Path("gameId") String gameId, @Path("invitationId") String invitationId, @Header("gToken") String token);

    @POST("games/{gameId}/invitation/{invitationId}/cancel")
    Call<Map<String, Boolean>> cancelInvitation(@Path("gameId") String gameId, @Path("invitationId") String invitationId, @Header("gToken") String token);

    @POST("games/{gameId}/invitation/{invitationId}/decline")
    Call<Map<String, Boolean>> declineInvitation(@Path("gameId") String gameId, @Path("invitationId") String invitationId, @Header("gToken") String token);

    @GET("games/{gameId}/invitation/{invitationId}/status")
    Call<QuInvitation> getInvitationStatus(@Path("gameId") String gameId, @Path("invitationId") String invitationId, @Header("gToken") String token);

    @POST("games/{gameId}/answers")
    Call<Map<String, Boolean>> submitAnswers(@Path("gameId") String gameId, @Body GamePlayStatus answers, @Header("gToken") String token);

}
