package com.rom.quizup.client.providers;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when a users Google+ ID has been assigned.
 */
public interface OnAssignGooglePlusIdCompleted {
  /**
   * Method that is called when a users Google+ ID has been assigned
   *
   * @param result The result of the call
   */
  void onAssignGooglePlusId(Boolean result);
}
