package com.rom.quizup.client.providers;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when an invitation has been accepted.
 */
public interface OnAcceptInvitationCompleted {
  /**
   * Method that is executed when an invitation has been accepted
   *
   * @param result The result of the call
   * @param gameId The game ID
   * @param invitationId The invitation ID
   */
  void onAcceptInvitationCompleted(Boolean result, String gameId, String invitationId);
}
