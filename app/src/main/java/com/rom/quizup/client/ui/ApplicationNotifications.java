package com.rom.quizup.client.ui;

import com.rom.quizup.client.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * The purpose of this class is to generate notifications
 */
public class ApplicationNotifications {

  /**
   * Accept invitation action
   */
  public static final int ACCEPT_INVITATION_ACTION = 1;
  /**
   * Decline invitation action
   */
  public static final int DECLINE_INVITATION_ACTION = 2;

  /**
   * Prompt invitation action
   */
  public static final int PROMPT_INVITATION_ACTION = 3;

  /**
   * Defines the notification ID for invitations
   */
  public static final int INVITATION_NOTIFICATION_ID = 0;


  /**
   * Defines the action extra in the notification intent
   */
  public static final String INVITATION_ACTION_EXTRA_ID = "playAction";
  /**
   * Defines the message extra in the notification intent
   */
  public static final String INVITATION_MESSAGE_EXTRA_ID = "message";
  /**
   * Defines the invitation ID extra in the notification intent
   */
  public static final String INVITATION_ID_EXTRA_ID = "invitationId";
  /**
   * Defines the player ID extra in the notification intent
   */
  public static final String PLAYER_ID_EXTRA_ID = "playerId";
  /**
   * Defines the Game ID extra in the notification intent
   */
  public static final String GAME_ID_EXTRA_ID = "gameId";
  /**
   * Defines the nickname extra in the notification intent
   */
  public static final String NICK_NAME_EXTRA_ID = "nickName";

  /**
   * Provides the ability to generate a notification
   *
   * @param context The context
   * @param message The message
   * @param gameId The game ID
   * @param invitationId The invitation ID
   * @param playerId The player ID
   * @param nickName The nickname
   */
  public static void generateInvitationNotification(Context context,
      String message,
      String gameId,
      String invitationId,
      String playerId,
      String nickName) {

    int icon = R.drawable.ic_stat_notify;
    long when = System.currentTimeMillis();
    String title = context.getString(R.string.app_name);
    NotificationManager notificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

    // Play Intent
    PendingIntent playIntent = buildInvitationIntent(context,
        gameId,
        invitationId,
        playerId,
        nickName,
        message,
        ACCEPT_INVITATION_ACTION);

    // Decline Intent
    PendingIntent declineIntent = buildInvitationIntent(context,
        gameId,
        invitationId,
        playerId,
        nickName,
        message,
        DECLINE_INVITATION_ACTION);

    // Prompt Intent
    PendingIntent promptIntent = buildInvitationIntent(context,
        gameId,
        invitationId,
        playerId,
        nickName,
        message,
        PROMPT_INVITATION_ACTION);

    Notification.Builder builder = new Notification.Builder(context);
    builder.setContentText(message);
    builder.setTicker(message);
    builder.setSmallIcon(icon);
    builder.setWhen(when);
    builder.setContentTitle(title);
    builder.setContentIntent(promptIntent);
    builder.addAction(0, context.getString(R.string.declineInvitation), declineIntent);
    builder.addAction(0, context.getString(R.string.acceptInvitation), playIntent);
    builder.setDeleteIntent(declineIntent);
    builder.setAutoCancel(true);
    builder.setPriority(Notification.PRIORITY_HIGH);

    Notification notification = builder.build();


    notificationManager.notify(INVITATION_NOTIFICATION_ID, notification);
  }

  /**
   * Builds the intent for the notification
   *
   * @param context The context
   * @param gameId The game ID
   * @param invitationId The invitation ID
   * @param playerId The player ID
   * @param nickName The nickname
   * @param message The message
   * @param invitationAction The action this intent represents
   * @return {@link PendingIntent}
   */
  private static PendingIntent buildInvitationIntent(Context context,
      String gameId,
      String invitationId,
      String playerId,
      String nickName,
      String message,
      int invitationAction) {
    Intent notificationIntent = new Intent(context, MainActivity.class);
    notificationIntent.putExtra(INVITATION_ID_EXTRA_ID, invitationId);
    notificationIntent.putExtra(GAME_ID_EXTRA_ID, gameId);
    notificationIntent.putExtra(PLAYER_ID_EXTRA_ID, playerId);
    notificationIntent.putExtra(NICK_NAME_EXTRA_ID, nickName);
    notificationIntent.putExtra(INVITATION_MESSAGE_EXTRA_ID, message);
    notificationIntent.putExtra(INVITATION_ACTION_EXTRA_ID, invitationAction);
    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    notificationIntent.addCategory(String.valueOf(invitationAction));
    PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

    return intent;
  }
}
