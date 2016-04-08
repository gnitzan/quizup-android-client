
package com.rom.quizup.client.ui.opponentslist.models;

/**
 * The purpose of this class is to be used for the opponents list view
 */
public class OpponentItem {
  public String id;
  public String content;
  public String imageUrl;
  public boolean isPlusId = false;

  /**
   * Constructor used for Google+ users
   *
   * @param id The Google+ ID
   * @param content The name to display
   * @param imageUrl The url of their profile image
   */
  public OpponentItem(String id, String content, String imageUrl) {
    this(id, content);
    this.imageUrl = imageUrl;
    this.isPlusId = true;
  }

  /**
   * Constructor used for adding a general opponent
   *
   * @param id The user's ID
   * @param content The name to display
   */
  public OpponentItem(String id, String content) {
    this.id = id;
    this.content = content;
  }

  /**
   * Returns the display name
   */
  @Override
  public String toString() {
    return content;
  }
}
