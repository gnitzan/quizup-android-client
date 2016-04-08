package com.rom.quizup.client.providers;

import com.rom.quizup.client.models.QuPlayer;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when the retrieval of a player's record has completed. Used in conjunction with
 * {@link DataProvider}
 */
public interface OnGetPlayerRecordCompleted {
  /**
   * Called when the player's record has been retrieved
   *
   * @param model The player's record model
   */
  void onGetPlayerRecordCompleted(QuPlayer model);
}
