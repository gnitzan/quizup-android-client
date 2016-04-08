package com.rom.quizup.client.ui.game;

import com.rom.quizup.client.R;
import com.rom.quizup.client.helpers.Location;
import com.rom.quizup.client.ui.game.models.LetterRenderModel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Represents a selected letter that is displayed while spelling a word
 */
public class QuestionAnswerLetter extends LinearLayout {
  TextView letterTextView;
  IGameManager gameManager;

  /**
   * Constructor
   *
   * @param context The context
   */
  public QuestionAnswerLetter(Context context) {
    super(context);
    this.setWillNotDraw(false);
    init(context);
  }

  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   */
  public QuestionAnswerLetter(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.setWillNotDraw(false);
    init(context);
  }

  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   * @param defStyle
   */
  public QuestionAnswerLetter(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.setWillNotDraw(false);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater.from(context).inflate(R.layout.fragment_game_questionanswer_letter, this);
    this.letterTextView =
        (TextView) this.findViewById(R.id.gameactivity_fragment_questionanswer_letter);
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
   * Sets the letter
   *
   * @param letterLocation The location of the letter
   */
  public void setLocation(Location letterLocation) {
    LetterRenderModel letter =
        this.gameManager.getBoardRenderModel().getGridLayout().getBoard().get(letterLocation);
    this.letterTextView.setText(letter.getGlyph().toString().toUpperCase());
  }
}
