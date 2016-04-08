package com.rom.quizup.client.providers;

import com.rom.quizup.client.models.QuInvitation;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;

/**
 * The purpose of this class is to make an asynchronous call to send an invitation to an opponent
 * using the opponent's Google+ Id
 *
 */
/*
public class SendInvitationByPlusIdTask extends ServiceTask<QuInvitation> {

  private OnSendInvitationCompleted listener;
  private String playerId;

  public SendInvitationByPlusIdTask(
      Quizup service, String playerId, OnSendInvitationCompleted listener) {
    super(service);
    this.listener = listener;
    this.playerId = playerId;
  }

  @Override
  protected void onPostExecute(QuInvitation result) {
    listener.onSendInvitationCompleted(result);
  }

  @Override
  protected QuInvitation executeEndpointCall(String... params) throws SocketTimeoutException, IOException {
    Invitation invitation =
        //service.gameEndpoint().startMultiplayerGameByGooglePlusId(1, playerId).execute();
    return new QuInvitation(invitation.getGameId(), invitation.getInvitationId());
  }
}
*/