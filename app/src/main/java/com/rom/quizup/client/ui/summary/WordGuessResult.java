package com.rom.quizup.client.ui.summary;

import android.annotation.SuppressLint;

/**
 * This class serves as a model for a multiplayer game's line item detailing each word and whether
 * or not the players answered the question correctly
 */
@SuppressLint("DefaultLocale")
public class WordGuessResult {

  /**
   * The word/answer
   */
  public String word;

  /**
   * <code>true</code> if the current player guessed correctly
   */
  public Boolean player1GuessedCorrect = false;

  /**
   * <code>true</code> if the opponent guessed correctly
   */
  public Boolean player2GuessedCorrect = false;

  /**
   * Constructor
   */
  public WordGuessResult() {}

  /**
   * Constructor
   *
   * @param word The word
   * @param player1Guessed <code>true</code> if the current player guessed correctly
   * @param player2Guessed <code>true</code> if the opponent guessed correctly
   */
  public WordGuessResult(String word, Boolean player1Guessed, Boolean player2Guessed) {
    this.word = word.toUpperCase();
    this.player1GuessedCorrect = player1Guessed;
    this.player2GuessedCorrect = player2Guessed;
  }
}
