package com.rom.quizup.client.providers;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when an invitation has been canceled.
 */
public interface OnCancelInvitationCompleted {
  /**
   * Method that is called when the invitation has been canceled
   *
   * @param result The result of the call
   */
  void onCancelInvitationCompleted(Boolean result);
}
