package com.rom.quizup.client.providers;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.QuInvitation;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;

import retrofit2.Call;


/**
 * The purpose of this class is to make an asynchronous call to determine if the opponent responded
 * to the invitation
 *
 */
public class GetOpponentResponseToInvitationTask extends ServiceTask<QuInvitation> {
  private String invitationId;
  private String gameId;
  private ApplicationSettings settings;
  private OnGetOpponentResponseToInvitationCompleted listener;

  public GetOpponentResponseToInvitationTask(Quizup service, ApplicationSettings settings, String gameId, String invitationId, OnGetOpponentResponseToInvitationCompleted listener) {
    super(service);
    this.listener = listener;
    this.invitationId = invitationId;
    this.gameId = gameId;
    this.settings = settings;
  }

  @Override
  protected void onPostExecute(QuInvitation result) {
    //TODO: check here
    listener.onGetOpponentResponseToInvitationCompleted(result);
  }

  @Override // Exceptions are handled in the base class
  protected QuInvitation executeEndpointCall(String... params) throws IOException {
    Call<QuInvitation> call = service.getGameServices().getInvitationStatus(gameId, invitationId, settings.getAuthToken());

    return call.execute().body();
  }
}
