package com.rom.quizup.client.ui.game;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;

import com.rom.quizup.client.R;
import com.rom.quizup.client.helpers.BoundedGrid;
import com.rom.quizup.client.helpers.Location;
import com.rom.quizup.client.ui.game.models.BoardRenderModel;
import com.rom.quizup.client.ui.game.models.LetterRenderModel;

/**
 * The purpose of this class is to support functionality for drawing the main game board view
 */
public class BoardView extends LinearLayout implements IGameView {
  IGameManager gameManager;
  LinearLayout[] rows;
  LinearLayout rootLayout;
  List<LetterView> letterViews = new ArrayList<LetterView>();
  private LinearLayout answerLetterLayout;
  Hashtable<Location, QuestionAnswerLetter> letterViewsHash;

  private static final String LOG_TAG = BoardView.class.getSimpleName();

  /**
   * Constructor
   *
   * @param context The context
   */
  public BoardView(Context context) {
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
  public BoardView(Context context, AttributeSet attrs) {
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
  public BoardView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.setWillNotDraw(false);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater.from(context).inflate(R.layout.fragment_game_board, this);

    setClipChildren(false);

    letterViewsHash = new Hashtable<Location, QuestionAnswerLetter>();
    rootLayout = (LinearLayout) this.findViewById(R.id.textlayout);

    answerLetterLayout = (LinearLayout) this.findViewById(R.id.answerTextLayout);

  }

  /**
   * Set the game manager
   */
  @Override
  public void setGameManager(IGameManager gameManager) {
    this.gameManager = gameManager;
  }

  @Override
  public void onGameManagerStateChange(GameStateType gameState) {}

  /**
   * Redraw the layout of the board
   */
  @Override
  public void onGameModelLoaded(BoardRenderModel boardRenderModel) {
    this.redrawLayout();
  }

  @Override
  public void onTick() {}

  @Override
  public void setFragmentManager(FragmentManager fragmentManager) {}

  @SuppressWarnings("deprecation")
  private void redrawLayout() {
    Log.d("BoardView", "Redraw Layout");

    if (this.rows != null) {
      for (LinearLayout layout : this.rows) {
        rootLayout.removeView(layout);
      }
    }
    letterViews.clear();

    BoundedGrid<LetterRenderModel> boardLayout =
        this.gameManager.getBoardRenderModel().getGridLayout().getBoard();
    rows = new LinearLayout[boardLayout.getNumRows()];
    for (int r = 0; r <= boardLayout.getNumRows() - 1; r++) {
      rows[r] = new LinearLayout(this.getContext());
      rows[r].setClipChildren(false);
      rows[r].setOrientation(LinearLayout.HORIZONTAL);
      rows[r].setId(r);
      rows[r].setGravity(Gravity.CENTER);
      LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
      rows[r].setLayoutParams(lp);
      for (int c = 0; c <= boardLayout.getNumCols() - 1; c++) {
        Location loc = new Location(r, c);
        LetterView letterView = new LetterView(this.getContext());
        letterView.setGameManager(gameManager);
        letterView.setLetterLocation(loc);
        letterView.setLayoutParams(
            new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        letterView.setGameManager(gameManager);
        letterViews.add(letterView);
        rows[r].addView(letterView);
      }
      rootLayout.addView(rows[r]);
    }
    this.invalidate();

  }

  /**
   * Notifies the game manager that a letter has been selected if the coordinates are in fact where
   * a letter exists
   */
  @Override
  public void onTouchMove(int x, int y) {
    try {
      for (LetterView letterView : letterViews) {
        if (letterView != null) {
          if (isViewContains(letterView, x, y)) {
            gameManager.onLetterSelected(letterView.getLetterLocation());
          }
        }
      }
    } catch (ConcurrentModificationException e) {
      Log.e(LOG_TAG, "onTouchMove", e);
      e.printStackTrace();
    }
  }

  private boolean isViewContains(LetterView view, int x, int y) {
    int w = view.getWidth();
    int h = view.getHeight();

    int[] loc = new int[2];
    view.getLocationOnScreen(loc);

    double offsetX = loc[0] + w / 2;
    double offsetY = loc[1] + h / 2;

    // Euclidean distance match strategy
    double dist = Math.sqrt(Math.pow(offsetX - x, 2) + Math.pow(offsetY - y, 2));

    if (dist <= w / 2) {
      Log.d("BoardView", "Distance calcuated at " + dist + " for event " + x + "," + y
          + " for view at " + loc[0] + "," + loc[1]);
    }

    return dist <= w / 2;
  }

  /**
   * Resets the letters
   */
  @Override
  public void onQuestionAnswered() {
    try {
      for (LetterView letterView : letterViews) {
        if (letterView != null) {
          letterView.reset();
        }
      }
    } catch (ConcurrentModificationException e) {
      Log.e(LOG_TAG, "onQuestionAnswered", e);
      e.printStackTrace();
    }

    updateLetterUI();
  }

  /**
   * Resets the letters
   */
  @Override
  public void onQuestionSkipped() {
    try {
      for (LetterView letterView : letterViews) {
        if (letterView != null) {
          letterView.reset();
        }
      }
    } catch (ConcurrentModificationException e) {
      Log.e(LOG_TAG, "onQuestionSkipped", e);
      e.printStackTrace();
    }

    updateLetterUI();

  }

  /**
   * Updates the letter UI
   */
  @Override
  public void updateUI() {
    for (LetterView letterView : letterViews) {
      if (letterView != null) {
        letterView.udateUI();
      }
    }
  }

  @Override
  public void onLetterAdded() {
    updateLetterUI();
  }

  @Override
  public void onLetterReset() {
    updateLetterUI();
  }

  private void updateLetterUI() {
    Queue<LetterRenderModel> selectedLetterQueue =
        gameManager.getBoardRenderModel().getGridLayout().getSelectedLetterQueue();
    if (!selectedLetterQueue.isEmpty()) {
      for (LetterRenderModel letterQueue : selectedLetterQueue) {
        Location loc =
            gameManager.getBoardRenderModel().getGridLayout().getBoard().get(letterQueue);
        if (loc != null) {
          if (!letterViewsHash.containsKey(loc)) {
            addToView(loc);
          }
        }
      }
    } else {
      answerLetterLayout.removeAllViews();
      letterViewsHash.clear();
    }
  }

  private void addToView(final Location letterLocation) {
    if (this.getContext() != null) {
      QuestionAnswerLetter letterContainer = new QuestionAnswerLetter(this.getContext());
      if (letterContainer != null) {
        letterContainer.setGameManager(gameManager);
        letterContainer.setLocation(letterLocation);
        letterViewsHash.put(letterLocation, letterContainer);
        answerLetterLayout.addView(letterContainer);
      }
    }
  }
}
