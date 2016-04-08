package com.rom.quizup.client.ui.game.models;

/**
 * Represents the question/clue
 */
public class QuestionRenderModel {
  private String question;
  private String word;
  private boolean completed = false;

  /**
   * Returns the question/clue
   *
   * @return {@link String}
   */
  public String getQuestion() {
    return question;
  }

  /**
   * Set the question
   *
   * @param question The question
   */
  public void setQuestion(String question) {
    this.question = question;
  }

  /**
   * Returns <code>true</code> if the question has been completed
   */
  public boolean getCompleted() {
    return completed;
  }

  /**
   * Set the question as completed
   *
   * @param completed <code>true</code> if completed
   */
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  /**
   * Returns the word/answer to the question
   *
   * @return {@link String}
   */
  public String getWord() {
    return word;
  }

  /**
   * Set the word/answer
   *
   * @param word The word
   */
  public void setWord(String word) {
    this.word = word;
  }
}
