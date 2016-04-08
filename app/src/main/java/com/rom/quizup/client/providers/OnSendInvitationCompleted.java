package com.rom.quizup.client.providers;

import com.rom.quizup.client.models.QuInvitation;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when an invitation has been sent. Used in conjunction with {@link DataProvider}
 */
public interface OnSendInvitationCompleted {
  /**
   * Called when the invitation has been sent
   *
   * @param result Details about the invitation
   */
  void onSendInvitationCompleted(QuInvitation result);
}
