package com.rom.quizup.client.models;

import java.io.Serializable;

/**
 * Defines a letter on a game board
 */
@SuppressWarnings("serial")
public class QuLetter implements Serializable {
  private Character letter;

  /**
   * Constructor
   *
   * @param letter The letter
   */
  public QuLetter(Character letter) {
    this.letter = letter;
  }

  /**
   * Get the letter
   *
   * @return {@link Character}
   */
  public Character getLetter() {
    return letter;
  }
}
