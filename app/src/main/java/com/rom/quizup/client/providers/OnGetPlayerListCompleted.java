package com.rom.quizup.client.providers;

import java.util.List;

import com.rom.quizup.client.models.Player;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when the retrieval of the player list is completed. Used in conjunction with
 * {@link DataProvider}
 */
public interface OnGetPlayerListCompleted {
  /**
   * Called when the player list has been loaded
   *
   * @param result The list of players {@link List}&lt;{@link Player}&gt;
   */
  void onGetPlayerListCompleted(List<Player> result);
}
