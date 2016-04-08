package com.rom.quizup.client.ui.lobby;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import com.rom.quizup.client.QuizupApplication;
import com.rom.quizup.client.R;
import com.rom.quizup.client.models.QuGame;
import com.rom.quizup.client.providers.OnGetGameCompleted;
import com.rom.quizup.client.providers.OnStartNewSinglePlayerGameCompleted;
import com.rom.quizup.client.ui.BaseActivity;
import com.rom.quizup.client.ui.IntentExtras;
import com.rom.quizup.client.ui.RequestCodes;
import com.rom.quizup.client.ui.game.GameActivity;

/**
 * The purpose of this activity is to provide a "lobby" for the player to wait while the game board
 * is being generated
 */
public class LobbyActivity extends BaseActivity implements OnStartNewSinglePlayerGameCompleted, OnGetGameCompleted {

  private QuGame model;
  private String gameId;

  private TextView countDownTextView, countdownMessageTextView, countdownNameTextView;

  /**
   * Initialize the views and start building the game board based upon single/multiplayer
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_lobby);

    //mApplication = (QuizupApplication)getApplication();

    startBuildingGame();
    countDownTextView = ((TextView) findViewById(R.id.countDownText));
    countdownMessageTextView = (TextView) findViewById(R.id.countDownMessage);
    countdownNameTextView = (TextView) findViewById(R.id.nameText);
  }

  /**
   * Pause the logo animation if it is running
   */
  @Override
  protected void onPause() {
    super.onPause();
  }

  /**
   * Start the logo animation if it is not running
   */
  @Override
  protected void onResume() {
    super.onResume();
  }

  /**
   * Inflate the menu; this adds items to the action bar if it is present.
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.lobby, menu);
    return true;
  }

  private void startBuildingGame() {
    int gameType = getIntent().getIntExtra(IntentExtras.LOBBY_GAME_TYPE, 0);
    gameId = getIntent().getStringExtra(IntentExtras.LOBBY_GAME_ID);

    switch (gameType) {
      case IntentExtras.LOBBY_SINGLE_PLAYER:
        mApplication.getDataProvider().startNewSinglePlayerGame(this);
        break;
      case IntentExtras.LOBBY_MULTI_PLAYER:
        mApplication.getDataProvider().getGame(gameId, this);
        break;
    }
  }

  private void gameBoardReady(QuGame model) {
    this.model = model;
    String nickName = model.getOpponent() == null ? null : model.getOpponent().getNickName();

    if (nickName != null) {
      countdownNameTextView.setText(String.format(getString(R.string.countdownOpponent), nickName));
      countdownMessageTextView.setText(R.string.countdownGetReadyToPlayOpponent);
    } else {
      countdownNameTextView.setText("");
      countdownMessageTextView.setText(getString(R.string.countdownGetReadyToPlay));
    }

    startCountdown();
  }

  private void startCountdown() {

    new CountDownTimer(4000, 1000) {
      @SuppressLint("DefaultLocale")
      @Override
      public void onTick(long millisUntilFinished) {
        countDownTextView.setVisibility(View.VISIBLE);
        countDownTextView.setText(
            String.format("%d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));
      }

      @Override
      public void onFinish() {
        Intent intent = new Intent(LobbyActivity.this, GameActivity.class);
        intent.putExtra(QuGame.TAG, model);
        startActivityForResult(intent, RequestCodes.REQUEST_GAME_RESULT);
      }
    }.start();
  }

  /**
   * Display the countdown timer
   */
  @Override
  public void onGetGameCompleted(QuGame result) {
    // Model can be null if there was no board for that level
    if (result != null) {
      gameBoardReady(result);
    }
  }

  /**
   * Display the countdown timer
   */
  @Override
  public void onStartNewSinglePlayerGameCompleted(QuGame result) {
    gameBoardReady(result);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case RequestCodes.REQUEST_GAME_RESULT:
        setResult(resultCode, data);
        finish();
        break;
    }
  }
}
