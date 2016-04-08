package com.rom.quizup.client.ui.game.models;

/**
 * Represents a letter on the game board
 */
public class LetterRenderModel {
  private Character glyph;
  private Boolean isSelected = false;

  /**
   * Gets the letter
   *
   * @return {@link Character}
   */
  public Character getGlyph() {
    return glyph;
  }

  /**
   * Set the letter
   *
   * @param glyph The letter
   */
  public void setGlyph(Character glyph) {
    this.glyph = glyph;
  }

  /**
   * Returns <code>true</code> if the letter is selected
   *
   * @return {@link Boolean}
   */
  public Boolean getIsSelected() {
    return isSelected;
  }

  /**
   * Sets the letter's selection
   *
   * @param isSelected <code>true</code> if the letter should be selected
   */
  public void setIsSelected(Boolean isSelected) {
    this.isSelected = isSelected;
  }
}
