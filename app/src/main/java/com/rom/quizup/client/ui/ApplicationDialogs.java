package com.rom.quizup.client.ui;

import com.rom.quizup.client.R;
import com.rom.quizup.client.helpers.ResourceHelpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.widget.Button;

/**
 * The purpose of this class is to support the basic functionality broadcasting an intent for
 * observing activities to show a simple dialog
 */
public final class ApplicationDialogs {

  /**
   * Alert message action
   */
  public static final String DISPLAY_ALERT_MESSAGE_ACTION = "alertMessageAction";

  /**
   * Button text intent extra name
   */
  public static final String BUTTON_TEXT = "buttonText";

  /**
   * Dialog title intent extra name
   */
  public static final String DIALOG_TITLE = "dialogTitle";

  /**
   * Dialog message intent extra name
   */
  public static final String EXTRA_MESSAGE = "message";

  /**
   * Provides the ability to broadcast an intent that contains all of the information necessary to
   * display a simple dialog
   *
   * @param context The context
   * @param title The title of the dialog
   * @param message The message of the dialog
   * @param buttonText The button text of the dialog
   */
  public static void displayAlert(
      Context context, String title, String message, String buttonText) {
    Intent intent = new Intent(DISPLAY_ALERT_MESSAGE_ACTION);
    intent.putExtra(EXTRA_MESSAGE, message);
    intent.putExtra(BUTTON_TEXT, buttonText);
    intent.putExtra(DIALOG_TITLE, title);
    context.sendBroadcast(intent);
  }

  /**
   * Provides the ability to broadcast an intent that contains all of the information necessary to
   * display a simple dialog
   *
   * @param context The context
   * @param title The title of the dialog
   * @param message The message of the dialog
   */
  public static void displayAlert(Context context, String title, String message) {
    String buttonText = context.getString(R.string.dialogButtonOK);
    displayAlert(context, title, message, buttonText);
  }

  /**
   * Provides the ability to broadcast an intent that contains all of the information necessary to
   * display a simple dialog
   *
   * @param context The context
   * @param message The message of the dialog
   */
  public static void displayAlert(Context context, String message) {
    String buttonText = context.getString(R.string.dialogButtonOK);
    String title = context.getString(R.string.app_name);
    displayAlert(context, title, message, buttonText);
  }

  /**
   * Provides the ability to style the dialog based upon application designs
   *
   * @param context The context
   * @param dialog The dialog
   */
  public static void styleDialogButtons(Context context, AlertDialog dialog) {

    Button button1 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
    Button button2 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
    Button button3 = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);

    ColorStateList buttonTextColors =
        ResourceHelpers.getColorStateList(context, R.drawable.dialog_button_text_selector_color);
    button1.setTextColor(buttonTextColors);
    button2.setTextColor(buttonTextColors);
    button3.setTextColor(buttonTextColors);

    button1.setBackground(
        context.getResources().getDrawable(R.drawable.button_background_state_selector));
    button2.setBackground(
        context.getResources().getDrawable(R.drawable.button_background_state_selector));
    button3.setBackground(
        context.getResources().getDrawable(R.drawable.button_background_state_selector));

  }
}
