package com.rom.quizup.client.providers;

import retrofit2.Call;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.QuInvitation;
import com.rom.quizup.client.models.User;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * The purpose of this class is to make an asynchronous call to send an invitation to an opponent
 *
 */
public class SendInvitationTask extends ServiceTask<QuInvitation> {

  private OnSendInvitationCompleted listener;
  private String playerId;
  private ApplicationSettings settings;

  public SendInvitationTask(Quizup service, ApplicationSettings settings, String playerId, OnSendInvitationCompleted listener) {
    super(service);
    this.listener = listener;
    this.playerId = playerId;
    this.settings = settings;
  }

  @Override
  protected void onPostExecute(QuInvitation result) {
    listener.onSendInvitationCompleted(result);
  }

  @Override
  protected QuInvitation executeEndpointCall(String... params) throws SocketTimeoutException, IOException {

    Call<QuInvitation> call = service.getGameServices().startMultiPlayerGame(playerId, 2, settings.getAuthToken());

    return call.execute().body();
  }
}
