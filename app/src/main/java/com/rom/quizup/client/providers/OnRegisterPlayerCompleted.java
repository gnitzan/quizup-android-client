package com.rom.quizup.client.providers;

import com.rom.quizup.client.models.QuPlayer;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when a player registration has completed.
 */
public interface OnRegisterPlayerCompleted {
  /**
   * Called when player registration has completed
   *
   * @param result The result of the registration
   */
  void onRegisterPlayerCompleted(QuPlayer result);
}
