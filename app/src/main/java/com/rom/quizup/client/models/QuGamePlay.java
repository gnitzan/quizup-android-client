package com.rom.quizup.client.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of this class is to provide a layer of abstraction between the service generated
 * classes for a game's player and the application
 */
@SuppressWarnings("serial")
public class QuGamePlay implements Serializable {
  private String playerId;
  private String nickName;
  private Long timeLeft;
  private List<Integer> answerIndexes;
  private Boolean isWinner;

  /**
   * Constructor
   *
   * @param playerId The player's ID
   * @param nickName The players Nickname
   * @param timeLeft The amount of time left in milliseconds when the player completed the game
   * @param answerIndexes A list of {@link Integer} indexes specifiying what answers the player had
   * @param isWinner The flag to determine if this player was the winner
   */
  public QuGamePlay(String playerId, String nickName, Long timeLeft, List<Integer> answerIndexes, Boolean isWinner) {
    this.playerId = playerId;
    this.nickName = nickName;
    this.timeLeft = timeLeft;
    this.answerIndexes = answerIndexes == null ? new ArrayList<Integer>() : answerIndexes;
    this.isWinner = isWinner;
  }

  /**
   * Get the player's ID
   *
   * @return {@link Long}
   */
  public String getPlayerId() {
    return playerId;
  }

  /**
   * Get the player's nickname
   *
   * @return {@link String}
   */
  public String getNickName() {
    return nickName;
  }

  /**
   * Get whether or not the player is the winner
   *
   * @return {@link Boolean}
   */
  public Boolean getIsWinner() {
    return isWinner;
  }


  /**
   * Set whether or not the player is the winner
   * Rom Added
   */
  public void setIsWinner(Boolean result)
  {
    isWinner = result;
  }

  /**
   * Get the amount of time left in milliseconds when the player completed the game
   *
   * @return {@link Long}
   */
  public Long getTimeLeft() {
    return timeLeft;
  }

  /**
   * set the time left in milliseconds
   *
   * @param timeLeft Time in milliseconds
   */
  public void setTimeLeft(Long timeLeft) {
    this.timeLeft = timeLeft;
  }

  /**
   * Get the list of indexes that the player answered
   *
   * @return {@link List}&lt;{@link Integer}&gt;
   */
  public List<Integer> getAnswerIndexes() {
    return answerIndexes;
  }

  /**
   * Add the index to the list of answers. If it already exists it isn't added.
   *
   * @param index The index to add
   */
  public void addAnswerIndex(Integer index) {

    if (answerIndexes == null) {
      answerIndexes = new ArrayList<Integer>();
    }

    if (!answerIndexes.contains(index)) {
      answerIndexes.add(index);
    }
  }
}
