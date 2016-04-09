package com.rom.quizup.client.models;

/**
 * The purpose of this class is to provide a layer of abstraction between the service generated
 * classes for a player and the application
 */
public class Player {
  private String id;
  private String nickname;
  private PlayerStatistics statistics;
  private String imageUrl;

  /**
   * Constructor
   *
   * @param id
   *          player's resource id.
   * @param nickname
   *          player's nickname.
   */
  public Player(String id, String nickname) {
    this.id = id;
    this.nickname = nickname;
    this.statistics = null;
  }

  /**
   * Constructor
   *
   * @param id
   *          player's resource id.
   * @param nickName
   *          player's nickname.
   * @param statistics
   *          player's game statistics.
   */
  public Player(String id, String nickName, PlayerStatistics statistics) {
    this.id = id;
    this.nickname = nickName;
    this.statistics = statistics;
  }

  /**
   * Gets the player's id.
   *
   */
  public String getId() {
    return id;
  }

  /**
   * Get the player's nickname.
   *
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * Get the player's statistics. Can be null.
   *
   */
  public PlayerStatistics getStatistics() {
    return statistics;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("\n------------------------------------------------------------------------------\n");
    sb.append("Player:\n");
    sb.append("playerId:    ").append(id).append("\n");
    sb.append("nickName:    ").append(nickname).append("\n");
    sb.append("imageUrl:    ").append(imageUrl).append("\n");
    sb.append("--------------------------------------------------------------------------------\n");

    return sb.toString();
  }
}
