package com.rom.quizup.client.ui.game;

/**
 * Represents the various states the GameActivity can transition into. States are exclusive and
 * generally progressive
 */
public enum GameStateType {
  GENERATING, PLAYING, QUESTION_TRANSITIONING, PAUSED, FINISHED
}
