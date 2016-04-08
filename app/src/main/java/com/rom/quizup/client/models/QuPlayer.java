package com.rom.quizup.client.models;

import java.io.Serializable;

/**
 * The purpose of this class is to provide a layer of abstraction between the service generated
 * classes for a player's record and the application
 */
@SuppressWarnings("serial")
public class QuPlayer implements Serializable {

  public static final String TAG = QuPlayer.class.getSimpleName();
  private String playerId;
  private String playerName;
  private Integer gamesWon;
  private Integer gamesPlayed;

  /**
   * Constructor
   *
   * @param playerName The player's name/nickname
   * @param gamesWon The number of multiplayer games won
   * @param gamesPlayed The number of multiplayer games played
   */
  public QuPlayer(String playerId, String playerName, Integer gamesWon, Integer gamesPlayed) {
    this.playerName = playerName;
    this.gamesWon = gamesWon;
    this.gamesPlayed = gamesPlayed;
  }

  /**
   * Get the player's name/nickname
   *
   * @return {@link String}
   */
  public String getPlayerName() {
    return this.playerName;
  }

  /**
   * Get the number of multiplayer games won
   *
   * @return {@link Integer}
   */
  public Integer getGamesWon() {
    return this.gamesWon;
  }

  /**
   * Get the number of multiplayer games played
   *
   * @return {@link Integer}
   */
  public Integer getGamesPlayed() {
    return this.gamesPlayed;
  }

  /**
   * Provides the ability to increment the players number of games played and games won within the
   * application. In memory only
   *
   * @param won {@link Boolean}
   */
  public void playedGame(Boolean won) {
    if (won != null) {

      if (gamesPlayed == null) {
        gamesPlayed = 0;
      }

      if (gamesWon == null) {
        gamesWon = 0;
      }

      this.gamesPlayed++;

      if (won) {
        gamesWon++;
      }
    }
  }

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }
}
