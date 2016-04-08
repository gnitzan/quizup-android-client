package com.rom.quizup.client.ui.game;

import com.rom.quizup.client.R;
import com.rom.quizup.client.ui.game.models.QuestionRenderModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Represents a string of letters that the user has selected to spell a word
 */
public class QuestionAnswerView extends Fragment {
  private static final String LOG_TAG = QuestionAnswerView.class.getSimpleName();
  private QuestionRenderModel wordRenderModel;
  TextView textViewSkip;

  IGameManager gameManager;

  /**
   * Initialize the answer layout and animations as well as setting up the skip button click event
   */
  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.d(LOG_TAG, "onCreateView");

    final ViewGroup rootView =
        (ViewGroup) inflater.inflate(R.layout.fragment_game_questionanswer, container, false);

    textViewSkip = (TextView) rootView.findViewById(R.id.textViewSkip);

    textViewSkip.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gameManager.onQuestionSkipped();
        rootView.invalidate();
      }
    });

    if (wordRenderModel != null) {
      TextView questionText = (TextView) rootView.findViewById(R.id.questionText);
      questionText.setText(wordRenderModel.getQuestion());
    }

    return rootView;
  }

  /**
   * Get the word model
   *
   */
  public QuestionRenderModel getWordRenderModel() {
    return wordRenderModel;
  }

  /**
   * Set the game manager
   *
   * @param gameManager An instance of {@link IGameManager}
   */
  public void setGameManager(IGameManager gameManager) {
    this.gameManager = gameManager;
  }

  /**
   * Set the word model
   *
   * @param wordRenderModel
   */
  public void setWordRenderModel(QuestionRenderModel wordRenderModel) {
    this.wordRenderModel = wordRenderModel;
  }
 }
