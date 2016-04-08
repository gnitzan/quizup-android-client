package com.rom.quizup.client.ui;

import com.rom.quizup.client.QuizupApplication;
import com.rom.quizup.client.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Base class for all activities
 */
public abstract class BaseActivity extends AppCompatActivity {

  private final AlertDialogBroadcastReceiver handleAlertMessageReceiver =
      new AlertDialogBroadcastReceiver();

  protected QuizupApplication mApplication;

  private boolean receiverRegistered = false;

  /**
   * Marks the application as inactive
   */
  @Override
  protected void onPause() {
    super.onPause();
    mApplication.setApplicationInactive();
  }

  /**
   * Marks the application as active and that a game is not currently being played
   */
  @Override
  protected void onResume() {
    super.onResume();
    mApplication.setApplicationActive();
    mApplication.setIsCurrentlyPlayingGame(false);
  }

  /**
   * Occurs when the activity is created
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mApplication = (QuizupApplication) getApplication();
    //mApplication.setApplicationActive();
  }

  /**
   * Registers any receivers when the activity starts
   */
  @Override
  protected void onStart() {
    registerReceiver();
    super.onStart();
  }

  /**
   * Unregisters any receivers and disconnects from Google+ when the activity stops
   */
  @Override
  protected void onStop() {
    unRegisterReceiver();

    super.onStop();
  }

  /**
   * Unregister any receivers when the activity finishes
   */
  @Override
  public void finish() {
    unRegisterReceiver();
    super.finish();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    registerReceiver();
  }

  protected void registerReceiver() {
    if (!receiverRegistered) {
      registerReceiver(handleAlertMessageReceiver,
          new IntentFilter(ApplicationDialogs.DISPLAY_ALERT_MESSAGE_ACTION));
      receiverRegistered = true;
    }
  }

  protected void unRegisterReceiver() {
    if (receiverRegistered) {
      unregisterReceiver(handleAlertMessageReceiver);
      receiverRegistered = false;
    }
  }

  /***
   * Initialize the contents of the Activity's standard options menu
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);

    return true;
  }

  /**
   * The purpose of this class is to act as a {@link BroadcastReceiver} for alert dialog messages
   */
  private class AlertDialogBroadcastReceiver extends BroadcastReceiver implements OnClickListener {

    @Override
    public void onReceive(Context context, Intent intent) {

      String newMessage = intent.getExtras().getString(ApplicationDialogs.EXTRA_MESSAGE);
      String buttonText = intent.getExtras().getString(ApplicationDialogs.BUTTON_TEXT);
      String title = intent.getExtras().getString(ApplicationDialogs.DIALOG_TITLE);

      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setMessage(newMessage);
      builder.setTitle(title);
      builder.setNeutralButton(buttonText, this);

      AlertDialog dialog = builder.create();

      dialog.show();

      ApplicationDialogs.styleDialogButtons(context, dialog);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
      dialog.dismiss();
    }
  }

  /**
   * Style an alert dialog with the application specific styles
   *
   * @param dialog An instance of an {@link AlertDialog}
   */
  public void styleDialogButtons(AlertDialog dialog) {
    ApplicationDialogs.styleDialogButtons(this, dialog);
  }
}
