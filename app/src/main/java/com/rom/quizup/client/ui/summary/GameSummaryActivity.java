package com.rom.quizup.client.ui.summary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.util.Timer;
import java.util.TimerTask;

import com.rom.quizup.client.R;
import com.rom.quizup.client.models.Game;
import com.rom.quizup.client.models.QuGame;
import com.rom.quizup.client.providers.OnGetGameCompleted;
import com.rom.quizup.client.providers.OnSubmitAnswersCompleted;
import com.rom.quizup.client.ui.BaseActivity;
import com.rom.quizup.client.ui.IntentExtras;
import com.rom.quizup.client.ui.WaitOverlayFragment;
import com.rom.quizup.client.ui.summary.SummaryListFragment.OnSummaryListFragmentInteractionListener;

/**
 * The purpose of this activity is to display a game summary to the player when the game is over
 */
public class GameSummaryActivity extends BaseActivity
    implements OnSummaryListFragmentInteractionListener, OnSubmitAnswersCompleted {

  private Timer timer;
  private boolean stopPolling = false;
  private SummaryListFragment summaryListFragment;
  private WaitOverlayFragment waitFragment;
  private QuGame quGame = null;

  /**
   * Cancel the timer and stop polling for the opponents results
   */
  @Override
  public void finish() {

    if (timer != null) {
      timer.cancel();
    }

    stopPolling = true;

    super.finish();
  }

  /**
   * This timer task is used to check the opponents game results every second until a response is
   * retrieved or the user backs out
   */
  private class CheckMultiPlayerGameCompleteResponse extends TimerTask
      implements OnGetGameCompleted {

    private String gameId = null;
    private Context context;

    public CheckMultiPlayerGameCompleteResponse(Context context, String gameId) {
      this.gameId = gameId;
      this.context = context;
    }

    @Override
    public void run() {
      mApplication.getDataProvider().getGame(gameId, this);
    }

    @Override
    public void onGetGameCompleted(QuGame result) {

      if (result == null || !result.getAreBothPlayersFinished()) {
        // run again in 1 second
        if (!stopPolling) {
          timer.schedule(new CheckMultiPlayerGameCompleteResponse(context, gameId), 1000);
        }
      } else {

        quGame = result;

        // set the return value
        Intent intent = new Intent();
        intent.putExtra(IntentExtras.SUMMARY_WON_GAME, result.getCurrentPlayer().getIsWinner());
        setResult(RESULT_OK, intent);

        summaryListFragment.hydrateState(result);
        getFragmentManager().beginTransaction().hide(waitFragment).commit();
      }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_game_summary);

    Bundle arguments = new Bundle();
    arguments.putString(
        WaitOverlayFragment.WAIT_OVERLAY_TEXT, getString(R.string.submittingAnswers));
    waitFragment = new WaitOverlayFragment();
    waitFragment.setArguments(arguments);

    getFragmentManager().beginTransaction().add(R.id.summary_list_fragment, waitFragment).commit();

    summaryListFragment =
        (SummaryListFragment) getFragmentManager().findFragmentById(R.id.summary_list_fragment);

    quGame = (QuGame) getIntent().getSerializableExtra(QuGame.TAG);

    submitGameData();
  }

  private void submitGameData() {
    mApplication.getDataProvider().submitAnswers(
        quGame.getId(), quGame.getCurrentPlayer().getAnswerIndexes(),
        quGame.getCurrentPlayer().getTimeLeft(), this);
  }

  /**
   * Inflate the menu; this adds items to the action bar if it is present.
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.game_summary, menu);
    return true;
  }

  @Override
  public void onFragmentInteraction() {}

  /**
   * If the answers were submitted correctly it begins polling for the opponents answers if the game
   * is multiplayer. If it is a single player game the summary screen is displayed.
   */
  @Override
  public void onSubmitAnswersCompleted(Boolean result) {

    if (result != null && result) {
      // if multiplayer game then get the opponents score
      if (quGame.getOpponent() != null) {
        waitFragment.setText(getString(R.string.waitingForOpponentToFinish));
        timer = new Timer();
        timer.schedule(new CheckMultiPlayerGameCompleteResponse(this, quGame.getId()), 0);
      } else {
        summaryListFragment.hydrateState(quGame);
        getFragmentManager().beginTransaction().hide(waitFragment).commit();
      }
    } else {
      // set the return value
      Intent intent = new Intent();
      intent.putExtra(IntentExtras.SUMMARY_FAILED_TO_SUBMIT_ANSWERS, true);
      setResult(RESULT_OK, intent);

      finish();
    }
  }
}
