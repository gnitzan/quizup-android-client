package com.rom.quizup.client.providers;

import com.rom.quizup.client.models.QuGame;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when starting a new single player game has completed. Used in conjunction with
 * {@link DataProvider}
 */
public interface OnStartNewSinglePlayerGameCompleted {
  /**
   * Called when a single player game has been created
   *
   * @param result The game model
   */
  void onStartNewSinglePlayerGameCompleted(QuGame result);
}
