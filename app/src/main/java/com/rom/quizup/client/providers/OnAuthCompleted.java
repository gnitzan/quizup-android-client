package com.rom.quizup.client.providers;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when a users Google+ ID has been assigned.
 */
public interface OnAuthCompleted {
  /**
   * Method that is called when
   *
   * @param result The result of the call
   */
  void onAuthCompleted(Boolean result);
}
