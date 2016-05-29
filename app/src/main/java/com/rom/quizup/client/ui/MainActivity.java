package com.rom.quizup.client.ui;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.rom.quizup.client.AboutActivity;
import com.rom.quizup.client.R;
import com.rom.quizup.client.helpers.DownloadImageTask;
import com.rom.quizup.client.helpers.OnDownloadImageCompleted;
import com.rom.quizup.client.models.QuPlayer;
import com.rom.quizup.client.providers.OnAcceptInvitationCompleted;
import com.rom.quizup.client.providers.OnDeclineInvitationCompleted;
import com.rom.quizup.client.ui.lobby.LobbyActivity;
import com.rom.quizup.client.ui.opponentslist.OpponentListActivity;

/**
 * The purpose of this activity is for providing the player with a main landing screen to display
 * their record and allow them to start a new game. This activity is also responsible for accepting
 * and declining multiplayer invites
 */
public class MainActivity extends BaseActivity
    implements
    OnDownloadImageCompleted,
    OnAcceptInvitationCompleted,
    OnDeclineInvitationCompleted {

  private static final String TAG = MainActivity.class.getSimpleName();

  private static QuPlayer quPlayer;
  private static Bitmap userProfileImage;

  private AlertDialog acceptingChallengeDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.d(TAG, "onCreate");

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    loadData();

    handleNotificationInvitationIntent(getIntent());


  }

  /**
   * Register receivers and handle the new intent
   */
  @Override
  protected void onNewIntent(Intent intent) {

    registerReceiver();
    handleNotificationInvitationIntent(intent);
    super.onNewIntent(intent);
  }

  /**
   * load the data from the intent and display the player's multiplayer record
   */
  protected void loadData() {

    // if the intent exists
    if (getIntent().hasExtra(QuPlayer.TAG)) {
      quPlayer = (QuPlayer) getIntent().getSerializableExtra(QuPlayer.TAG);
    }

    displayPlayerRecord();
  }

  /**
   * React to activity results such as the ending of a game, switching accounts, and Google+
   * authorization
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case RequestCodes.REQUEST_GAME_RESULT:
        if (data != null && data.getExtras().containsKey(IntentExtras.SUMMARY_WON_GAME)) {
          Boolean wonGame = data.getBooleanExtra(IntentExtras.SUMMARY_WON_GAME, false);
          refreshRecord(wonGame);
        } else if (data != null
            && data.getExtras().containsKey(IntentExtras.SUMMARY_FAILED_TO_SUBMIT_ANSWERS)) {
          ApplicationDialogs.displayAlert(this, getString(R.string.failedToSubmitAnswers));
        }
        break;
      case RequestCodes.REQUEST_SWITCHED_ACCOUNTS:
        if (data != null && data.getExtras().containsKey(IntentExtras.SETTINGS_SWITCHED_ACCOUNT)) {
          userProfileImage = null;
          quPlayer =
              (QuPlayer) data.getSerializableExtra(IntentExtras.SETTINGS_SWITCHED_ACCOUNT);
        }
        displayPlayerRecord();
        break;
      case RequestCodes.REQUEST_GOOGLE_PLUS_AUTHORIZATION:
        if (resultCode == RESULT_CANCELED) {
          displayPlayerRecord();
        }
        break;
    }
  }

  private void refreshRecord(Boolean wonGame) {
    if (wonGame != null) {
      if (quPlayer != null) {
        quPlayer.playedGame(wonGame);
        displayPlayerRecord();
      }
    }
  }

  /**
   * Occurs when the user clicks the button to start a single player game
   *
   * @param v The View
   */
  public void onSinglePlayerButtonClick(View v) {
    Intent intent = new Intent(this, LobbyActivity.class);
    intent.putExtra(IntentExtras.LOBBY_GAME_TYPE, IntentExtras.LOBBY_SINGLE_PLAYER);
    startActivityForResult(intent, RequestCodes.REQUEST_GAME_RESULT);
  }

  /**
   * Occurs when the user clicks the button to challenge an opponent
   *
   * @param v The View
   */
  public void onChallengeOpponentButtonClick(View v) {
    startActivityForResult(
        new Intent(this, OpponentListActivity.class), RequestCodes.REQUEST_GAME_RESULT);
  }

  /**
   * Occurs when the user clicks the button to go to about me
   *
   * @param v The View
   */
  public void onAboutButtonClick(View v) {
    Intent intent = new Intent(this, AboutActivity.class);


  }

  /**
   * Display the user's record
   */
  private void displayPlayerRecord() {

    if (quPlayer == null) {
      return;
    }

    ImageView profileImage = (ImageView) findViewById(R.id.user_image);
    TextView text = (TextView) findViewById(R.id.userNameText);

    if (userProfileImage != null) {
      profileImage.setImageBitmap(userProfileImage);
      profileImage.invalidate();
    } else {
      profileImage.setImageResource(R.drawable.avatar_large);
      profileImage.invalidate();
    }

    if (quPlayer.getPlayerName() != null) {
      text.setText(quPlayer.getPlayerName());
    }
    else {
      text.setText(mApplication.getSelectedAccountName());
    }

    TextView stats = (TextView) findViewById(R.id.userStatsText);

    if (quPlayer.getGamesPlayed() > 0) {

      String statsText = String.format(
          getString(R.string.userStatsText), quPlayer.getGamesWon(), quPlayer.getGamesPlayed());
      stats.setText(statsText);
    } else {
      stats.setText(getString(R.string.noGamesPlayedMessage));
    }

    if (quPlayer.getImgUrl() != null) {
      new DownloadImageTask(profileImage, this).execute(quPlayer.getImgUrl());
    }

  }

  /**
   * Assign a local variable to the user's profile image from {@link GoogleSignInAccount} for caching purposes
   */
  @Override
  public void onDownloadImage(Bitmap image) {
    userProfileImage = image;
  }

  /**
   * Process the intent by checking if it is a notification based intent and either accepting,
   * declining, or display a dialog to the user prompting them with an accept/decline dialog.
   *
   * @param intent The {@link Intent}
   */
  protected void handleNotificationInvitationIntent(Intent intent) {

    if (intent != null && intent.getExtras() != null
        && intent.getExtras().containsKey(ApplicationNotifications.INVITATION_ID_EXTRA_ID)) {

      String playerId = intent.getStringExtra(ApplicationNotifications.PLAYER_ID_EXTRA_ID);

      if (mApplication.getCurrentPlayerId().compareTo(playerId) == 0) {

        NotificationManager notificationManager =
            (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(0);


        String invitationId = intent.getStringExtra(ApplicationNotifications.INVITATION_ID_EXTRA_ID);
        String gameId = intent.getStringExtra(ApplicationNotifications.GAME_ID_EXTRA_ID);

        int playAction = intent.getIntExtra(ApplicationNotifications.INVITATION_ACTION_EXTRA_ID, 0);

        switch (playAction) {
          case ApplicationNotifications.ACCEPT_INVITATION_ACTION:
            showAcceptingChallengeDialog();
            mApplication.getDataProvider().acceptInvitation(gameId, invitationId, this);
            break;
          case ApplicationNotifications.DECLINE_INVITATION_ACTION:
            mApplication.getDataProvider().declineInvitation(gameId, invitationId, this);
            break;
          case ApplicationNotifications.PROMPT_INVITATION_ACTION:
            String message =
                intent.getStringExtra(ApplicationNotifications.INVITATION_MESSAGE_EXTRA_ID);
            showInvitationDialog(message, invitationId, gameId);
            break;
        }
      } else {
        String nickName = intent.getStringExtra(ApplicationNotifications.NICK_NAME_EXTRA_ID);
        ApplicationDialogs.displayAlert(
            this, String.format(getString(R.string.invitationIntendedForAnotherUser), nickName));
      }
    }
  }

  /**
   * Dismiss the accepting challenge dialog because we're about to navigate to the lobby
   */
  private void hideAcceptingChallengeDialog() {
    if (acceptingChallengeDialog != null) {
      acceptingChallengeDialog.dismiss();
    }
  }

  /**
   * Display a dialog to the user while the application is accepting the challenge.
   */
  private void showAcceptingChallengeDialog() {

    if (acceptingChallengeDialog == null) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);

      builder.setMessage(R.string.acceptingChallenge);
      builder.setTitle(R.string.app_name);
      builder.setCancelable(false);
      acceptingChallengeDialog = builder.create();
    }

    acceptingChallengeDialog.show();

    styleDialogButtons(acceptingChallengeDialog);
  }

  /**
   * Display a dialog to the user prompting them to accept/decline the invitation
   *
   * @param message The message to display
   * @param invitationId The invitation ID
   * @param gameId The game ID
   */
  private void showInvitationDialog(String message, final String invitationId, final String gameId) {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setNegativeButton(R.string.declineInvitation, new OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {
        mApplication.getDataProvider().declineInvitation(gameId, invitationId, MainActivity.this);
      }
    });

    builder.setPositiveButton(R.string.acceptInvitation, new OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {
        showAcceptingChallengeDialog();
        mApplication.getDataProvider().acceptInvitation(gameId, invitationId, MainActivity.this);

      }
    });

    builder.setMessage(message);
    builder.setTitle(R.string.app_name);
    builder.setCancelable(false);
    AlertDialog inviteDialog = builder.create();

    inviteDialog.show();

    styleDialogButtons(inviteDialog);
  }

  /**
   * If the declining of an invitation fails, display an alert to the user
   */
  @Override
  public void onDeclineInvitationCompleted(Boolean result) {
    if (!result) {
      ApplicationDialogs.displayAlert(this, getString(R.string.unableToDeclineInvitation));
    }
  }

  /**
   * Navigate to the lobby activity if accepting the invitation was successful else display a
   * message to the user that the sender must have canceled
   */
  @Override
  public void onAcceptInvitationCompleted(Boolean result, String gameId, String invitationId) {

    hideAcceptingChallengeDialog();

    // evaluate the result...if false, the challenger must have cancelled
    if (result) {
      Intent intent = new Intent(this, LobbyActivity.class);
      intent.putExtra(IntentExtras.LOBBY_GAME_TYPE, IntentExtras.LOBBY_MULTI_PLAYER);
      intent.putExtra(IntentExtras.LOBBY_GAME_ID, gameId);
      startActivityForResult(intent, RequestCodes.REQUEST_GAME_RESULT);
    } else {
      ApplicationDialogs.displayAlert(this, this.getString(R.string.challengerCancelledCantAccept));
    }
  }

  /**
   * If the options menu matches settings then navigate to the settings screen
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    //TODO: remove
    return super.onOptionsItemSelected(item);
  }
}
