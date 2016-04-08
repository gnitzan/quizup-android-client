package com.rom.quizup.client.providers;

import com.rom.quizup.client.models.QuInvitation;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when the call to determine what an opponents response to an invitation is.
 */
public interface OnGetOpponentResponseToInvitationCompleted {
  /**
   * Method is called when the call has completed
   *
   */
  void onGetOpponentResponseToInvitationCompleted(QuInvitation invitation);
}
