package com.rom.quizup.client.ui.game;

import com.rom.quizup.client.R;
import com.rom.quizup.client.helpers.Location;
import com.rom.quizup.client.ui.game.models.LetterRenderModel;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The purpose of this class is to support the main board layout for a single letter
 */
@SuppressLint("DefaultLocale")
public class LetterView extends LinearLayout implements OnTouchListener {

  IGameManager gameManager;
  private Location letterLocation;

  FragmentManager fragmentManager;
  TextView letterTextView;
  Boolean isSelected = false;

  AnimatorSet selectedAnimationSet;
  AnimatorSet unselectedAnimationSet;
  AnimatorSet resetAnimationSet;
  Animation bounceAnimation;

  /**
   * Constructor
   *
   * @param context The context
   */
  public LetterView(Context context) {
    super(context);

    init(context);
  }

  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   */
  public LetterView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   * @param defStyle
   */
  public LetterView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater.from(context).inflate(R.layout.fragment_game_letter, this);
    letterTextView = (TextView) this.findViewById(R.id.fragment_game_letter_letterTextView);

    letterTextView.setOnTouchListener(this);

    bounceAnimation =
        AnimationUtils.loadAnimation(context, R.animator.game_letter_bounce_animation);

    selectedAnimationSet = (AnimatorSet) AnimatorInflater.loadAnimator(
        this.getContext(), R.animator.game_letter_selected);
    selectedAnimationSet.setTarget(this);
    selectedAnimationSet.addListener(new AnimatorListener() {

      @Override
      public void onAnimationStart(Animator arg0) {
        letterTextView.setTextColor(getContext().getResources().getColor(R.color.white));
        letterTextView.setBackgroundColor(getContext().getResources().getColor(R.color.red));
        letterTextView.setBackgroundResource(R.drawable.game_letter_roundedcorner_red);
      }

      @Override
      public void onAnimationRepeat(Animator arg0) {}

      @Override
      public void onAnimationEnd(Animator arg0) {}

      @Override
      public void onAnimationCancel(Animator arg0) {}
    });

    unselectedAnimationSet = (AnimatorSet) AnimatorInflater.loadAnimator(
        this.getContext(), R.animator.game_letter_unselected);
    unselectedAnimationSet.setTarget(this);
    unselectedAnimationSet.addListener(new AnimatorListener() {

      @Override
      public void onAnimationStart(Animator arg0) {
        letterTextView.setTextColor(getContext().getResources().getColor(R.color.black));
        letterTextView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        letterTextView.setBackgroundResource(R.drawable.game_letter_roundedcorner);
      }

      @Override
      public void onAnimationRepeat(Animator arg0) {}

      @Override
      public void onAnimationEnd(Animator arg0) {}

      @Override
      public void onAnimationCancel(Animator arg0) {}
    });

    resetAnimationSet = (AnimatorSet) AnimatorInflater.loadAnimator(
        this.getContext(), R.animator.game_letter_reset);
    resetAnimationSet.setTarget(this);
    resetAnimationSet.addListener(new AnimatorListener() {

      @Override
      public void onAnimationStart(Animator arg0) {}

      @Override
      public void onAnimationRepeat(Animator arg0) {}

      @Override
      public void onAnimationEnd(Animator arg0) {
        letterTextView.setTextColor(getContext().getResources().getColor(R.color.black));
        letterTextView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        letterTextView.setBackgroundResource(R.drawable.game_letter_roundedcorner);
      }

      @Override
      public void onAnimationCancel(Animator arg0) {}
    });

  }

  /**
   * Handles the animations when a tile is selected and not selected
   */
  public void udateUI() {
    LetterRenderModel renderModel =
        gameManager.getBoardRenderModel().getGridLayout().getBoard().get(letterLocation);
    if (renderModel.getIsSelected() && !this.isSelected) {
      isSelected = true;
      selectedAnimationSet.start();
      Vibrator vibrationService =
          (Vibrator) this.getContext().getSystemService(Context.VIBRATOR_SERVICE);
      vibrationService.vibrate(10);
    } else if (this.isSelected && !renderModel.getIsSelected()) {
      isSelected = false;
      unselectedAnimationSet.start();
    }
  }

  /**
   * Sets the fragment manager
   *
   * @param fragmentManager The {@link FragmentManager}
   */
  public void setFragmentManager(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
  }

  private LetterRenderModel getLetterAtLocation() {
    LetterRenderModel letter =
        gameManager.getBoardRenderModel().getGridLayout().getBoard().get(letterLocation);
    return letter;
  }

  /**
   * Sets the location of a specific letter on the grid
   *
   * @param letterLocation The location of the letter
   */
  public void setLetterLocation(Location letterLocation) {
    this.letterLocation = letterLocation;
    LetterRenderModel letter =
        gameManager.getBoardRenderModel().getGridLayout().getBoard().get(letterLocation);
    this.letterTextView.setText(letter.getGlyph().toString().toUpperCase());
  }

  /**
   * Gets the location of a letter
   *
   * @return {@link Location}
   */
  public Location getLetterLocation() {
    return this.letterLocation;
  }

  /**
   * Sets the game manager
   *
   * @param gameManager An instance of {@link IGameManager}
   */
  public void setGameManager(IGameManager gameManager) {
    this.gameManager = gameManager;
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_MOVE:
        return this.gameManager.onTouchEvent(event);
      case MotionEvent.ACTION_UP:
        return this.gameManager.onTouchEvent(event);
    }
    return false;
  }

  public void reset() {
    if (getLetterAtLocation() != null) {
      if (isSelected) {
        resetAnimationSet.start();
        this.startAnimation(bounceAnimation);
      }
    }
  }
}
