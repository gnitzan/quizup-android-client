package com.rom.quizup.client.providers;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when an invitation has been declined.
 */
public interface OnDeclineInvitationCompleted {
  /**
   * Method that is called when the invitation has been declined
   *
   * @param result The result of the call
   */
  void onDeclineInvitationCompleted(Boolean result);
}
