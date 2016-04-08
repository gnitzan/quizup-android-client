package com.rom.quizup.client.ui;

/**
 * The purpose of this class is to manage all intent extras
 */
public class IntentExtras {

  /**
   * Intent extra that is sent when the user has won the game
   */
  public static final String SUMMARY_WON_GAME = "wonGame";

  /**
   * Intent extra that is sent when the application failed to submit the answers
   */
  public static final String SUMMARY_FAILED_TO_SUBMIT_ANSWERS = "failedToSubmitAnswers";

  /**
   * Intent extra name indicating that the user switched accounts
   */
  public static final String SETTINGS_SWITCHED_ACCOUNT = "switchedAccount";

  /**
   * Intent extra name indicating that the user enabled Google+
   */
  public static final String SETTINGS_ENABLED_GOOGLE_PLUS = "enableGooglePlus";

  /**
   * Single Player Game Type
   */
  public static final int LOBBY_SINGLE_PLAYER = 1;

  /**
   * Multiplayer Game Type
   */
  public static final int LOBBY_MULTI_PLAYER = 2;

  /**
   * Game type intent extra
   */
  public static final String LOBBY_GAME_TYPE = "GameType";

  /**
   * Game ID intent extra
   */
  public static final String LOBBY_GAME_ID = "gameId";
}
