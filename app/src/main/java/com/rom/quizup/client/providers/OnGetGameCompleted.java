package com.rom.quizup.client.providers;

import com.rom.quizup.client.models.QuGame;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when the retrieval of a game has completed. Used in conjunction with
 * {@link DataProvider}
 */
public interface OnGetGameCompleted {
  /**
   * Called when the retrieval of the game has completed
   *
   * @param result The {@link QuGame} result
   */
  void onGetGameCompleted(QuGame result);
}
