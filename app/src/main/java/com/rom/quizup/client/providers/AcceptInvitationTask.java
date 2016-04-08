package com.rom.quizup.client.providers;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to accept an invitation
 *
 */
public class AcceptInvitationTask extends ServiceTask<Map<String, Boolean>> {
  private ApplicationSettings settings;
  private OnAcceptInvitationCompleted listener;
  private String invitationId;
  private String gameId;

  public AcceptInvitationTask(Quizup service, ApplicationSettings settings, String gameId, String invitationId, OnAcceptInvitationCompleted listener) {
    super(service);
    this.settings = settings;
    this.listener = listener;
    this.invitationId = invitationId;
    this.gameId = gameId;
  }

  @Override
  protected void onPostExecute(Map<String, Boolean> result) {
    boolean success = false;
    if (result != null) {
      // Accepting invitation thrown an exception
      success = result.get("success");
    }

    listener.onAcceptInvitationCompleted(success, gameId, invitationId);
  }

  @Override // Exceptions are handled in the base class.
  protected Map<String, Boolean> executeEndpointCall(String... params) throws IOException {

    Call<Map<String, Boolean>> call = service.getGameServices().acceptInvitation(gameId, invitationId, settings.getAuthToken());

    return call.execute().body();
  }
}
