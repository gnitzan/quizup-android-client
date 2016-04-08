package com.rom.quizup.client.models;

import java.io.Serializable;

/**
 * Defines a question and answer for the game
 */
@SuppressWarnings("serial")
public class QuQuestion implements Serializable {
  private String question;
  private QuWord word;

  /**
   * Constructor
   *
   * @param question The question
   * @param word The answer
   */
  public QuQuestion(String question, QuWord word) {
    this.question = question;
    this.word = word;
  }

  /**
   * Get the question
   *
   * @return {@link String}
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Get the word/answer
   *
   * @return {@link QuWord}
   */
  public QuWord getWord() {
    return word;
  }
}
