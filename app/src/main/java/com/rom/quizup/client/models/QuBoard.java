package com.rom.quizup.client.models;


import java.io.Serializable;
import java.util.List;

/**
 * The purpose of this class is to provide a layer of abstraction between the service generated
 * classes for a game board and the application
 */
@SuppressWarnings("serial")
public class QuBoard implements Serializable {
  private List<List<Character>> boardDefinition;
  private List<QuQuestion> questions;
  private Long allottedTime;

  /**
   * Get the list of questions
   *
   * @return {@link List}&lt;{@link QuQuestion}&gt;
   */
  public List<QuQuestion> getQuestions() {
    return questions;
  }

  /**
   * Get the allotted time the player has to solve the entire game board
   *
   * @return {@link Long}
   */
  public Long getAllottedTime() {
    return allottedTime;
  }

  /**
   * Get the board definition
   *
   * @return {@link List}&lt;{@link List}&lt;{@link Character}&gt;&gt;
   */
  public List<List<Character>> getBoardDefinition() {
    return boardDefinition;
  }

  /**
   * Constructor
   *
   * @param boardDefinition The board definition
   * @param questions The questions
   * @param allottedTime The allotted time to finish the game
   */
  public QuBoard(
          List<List<Character>> boardDefinition, List<QuQuestion> questions, Long allottedTime) {
    this.questions = questions;
    this.allottedTime = allottedTime;
    this.boardDefinition = boardDefinition;
  }
}
