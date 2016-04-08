package com.rom.quizup.client.models;


import java.io.Serializable;
import java.util.List;

/**
 * Defines an answer for a question in the game
 */
@SuppressWarnings("serial")
public class QuWord implements Serializable {
  private List<QuLetter> letters;

  /**
   * Constructor
   *
   * @param letters The list of letters that make up the word
   */
  public QuWord(List<QuLetter> letters) {
    this.letters = letters;
  }

  /**
   * Get the list of letters
   *
   * @return {@link List}&lt;{@link QuLetter}&gt;
   */
  public List<QuLetter> getLetters() {
    return this.letters;
  }

  /**
   * Get the {@link String} representation of the word/answer
   *
   * @return {@link String}
   */
  @Override
  public String toString() {

    StringBuilder wordString = new StringBuilder();

    if (letters != null) {
      for (QuLetter letter : letters) {
        wordString.append(letter.getLetter());
      }
    }

    return wordString.toString();
  }
}
