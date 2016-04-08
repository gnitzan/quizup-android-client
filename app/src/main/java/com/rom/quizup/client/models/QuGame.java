package com.rom.quizup.client.models;

import java.io.Serializable;


/**
 * The purpose of this class is to provide a layer of abstraction between the service generated
 * classes for a game and the application
 */
@SuppressWarnings("serial")
public class QuGame implements Serializable {

  public static final String TAG = QuGame.class.getName();

  private String id;
  private QuBoard board;
  private QuGamePlay currentPlayer;
  private QuGamePlay opponent;
  private Boolean areBothPlayersFinished = false;

  /**
   * Constructor
   *
   * @param id The game ID
   * @param board The board definition
   * @param currentPlayer The current player
   * @param opponent The opponent
   * @param areBothPlayersFinished Flag to specify if both players are finished
   */
  public QuGame(String id, QuBoard board, QuGamePlay currentPlayer,
                QuGamePlay opponent, Boolean areBothPlayersFinished) {
    this.id = id;
    this.board = board;

    this.opponent = opponent;
    this.currentPlayer = currentPlayer;
    this.areBothPlayersFinished = areBothPlayersFinished;
  }

  /**
   * Get the game ID
   *
   * @return {@link Long}
   */
  public String getId() {
    return id;
  }

  /**
   * Get the board model
   *
   * @return {@link QuBoard}
   */
  public QuBoard getBoard() {
    return board;
  }

  /**
   * Get the current player
   *
   * @return {@link QuGamePlay}
   */
  public QuGamePlay getCurrentPlayer() {
    return currentPlayer;
  }

  /**
   * Get the opponent
   *
   * @return {@link QuGamePlay}
   */
  public QuGamePlay getOpponent() {
    return opponent;
  }

  /**
   * Get if both players are finished
   *
   * @return {@link Boolean}
   */
  public Boolean getAreBothPlayersFinished() {
    return areBothPlayersFinished;
  }
}
