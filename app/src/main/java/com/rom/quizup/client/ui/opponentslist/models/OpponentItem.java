
package com.rom.quizup.client.ui.opponentslist.models;

/**
 * The purpose of this class is to be used for the opponents list view
 */
public class OpponentItem {
  public String id;
  public String content;
  public String imageUrl;

  /**
   * Constructor used for Google+ users
   *
   * @param id The Google+ ID
   * @param content The name to display
   * @param imageUrl The url of their profile image
   */
  public OpponentItem(String id, String content, String imageUrl) {
    this.id = id;
    this.content = content;
    this.imageUrl = imageUrl;
  }

  /**
   * Returns the display name
   */
  @Override
  public String toString() {
    return content;
  }
}
