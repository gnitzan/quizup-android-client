package com.rom.quizup.client.ui.game;

import com.rom.quizup.client.helpers.Location;
import com.rom.quizup.client.ui.game.models.BoardRenderModel;

import android.view.MotionEvent;

/**
 * Represents an architectural api for the game engine
 */
public interface IGameManager {

  /**
   * The current state of the game engine
   */
  GameStateType getState();

  /**
   * The render model for the game engines
   */
  BoardRenderModel getBoardRenderModel();

  /**
   * Returns true if the render model has been initialized in the engine Generally represents if the
   * game is ready for playback
   */
  Boolean isBoardRenderModelLoaded();

  /**
   * Api for transitioning the game engine into a particular GameState
   *
   * @param gameState
   */
  void goToGameState(GameStateType gameState);

  /**
   * Called when a question is skipped by the user
   */
  void onQuestionSkipped();

  /**
   * Called when a letter is selected by the user
   *
   * @param letterRenderModel
   */
  void onLetterSelected(Location letterRenderModel);

  /**
   * Called when a question is answered by the user
   */
  void onQuestionAnswered();

  /**
   * Notifies the game engine that a touch event has occurred
   *
   * @param event
   */
  boolean onTouchEvent(MotionEvent event);

  /**
   * Forced the game engine to simulate on the current render model This typically implies that a
   * client needs the engine to update based on the current timestamp
   */
  void simulate();
}
