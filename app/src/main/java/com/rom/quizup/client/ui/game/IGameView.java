package com.rom.quizup.client.ui.game;

import com.rom.quizup.client.ui.game.models.BoardRenderModel;

import android.support.v4.app.FragmentManager;

/**
 * Represents a Game view that receives game manager updates
 */
public interface IGameView {

  /**
   * Inject reference of the owner Game Manager
   *
   * @param gameManager
   */
  void setGameManager(IGameManager gameManager);

  /**
   * Used to communicate the game activity fragment context into sub-views
   *
   * @param fragmentManager
   */
  void setFragmentManager(FragmentManager fragmentManager);

  /**
   * Called when the GameManager GameState changes
   *
   * @param gameState
   */
  void onGameManagerStateChange(GameStateType gameState);

  /**
   * Called after a persisted game model is loaded and initialized in the game architecture
   *
   * @param boardRenderModel
   */
  void onGameModelLoaded(BoardRenderModel boardRenderModel);

  /**
   * Called when a question is answered
   */
  void onQuestionAnswered();

  /**
   * Called with a question is skipped
   */
  void onQuestionSkipped();

  /**
   * Called when the game engine completes one cycle of simulation
   */
  void onTick();

  /**
   * Called when a letter is either classified or unclassified
   */
  void onLetterAdded();

  /**
   * Called when the letters are reset on the gameboard
   */
  void onLetterReset();

  /**
   * Called when the game engine is ready to notify of a touch move event by the user
   *
   * @param x
   * @param y
   */
  void onTouchMove(int x, int y);

  /**
   * Called by the game engine after a similuation cycle is ready for rendition
   */
  void updateUI();
}
