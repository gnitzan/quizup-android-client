package com.rom.quizup.client.providers;


import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to decline an invitation
 *
 */
public class DeclineInvitationTask extends ServiceTask<Map<String, Boolean>> {

  private String invitationId;
  private String gameId;
  private OnDeclineInvitationCompleted listener;
  private ApplicationSettings settings;

  public DeclineInvitationTask(Quizup service, ApplicationSettings settings, String gameId, String invitationId, OnDeclineInvitationCompleted listener) {
    super(service);
    this.listener = listener;
    this.invitationId = invitationId;
    this.gameId = gameId;
    this.settings = settings;
  }

  @Override
  protected void onPostExecute(Map<String, Boolean> result) {
    boolean success = false;

    if (result != null && result.get("success").equals(true)) {
      // Declining the invitation thrown an exception.
      success = true;
    }

    listener.onDeclineInvitationCompleted(success);
  }

  @Override // Exceptions are handled in the base class.
  protected Map<String, Boolean> executeEndpointCall(String... params) throws IOException {
    Call<Map<String, Boolean>> call = service.getGameServices().declineInvitation(gameId, invitationId, settings.getAuthToken());

    return call.execute().body();
  }
}
