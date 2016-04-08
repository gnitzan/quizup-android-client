package com.rom.quizup.client.gcm;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.QuizupApplication;
import com.rom.quizup.client.ui.ApplicationNotifications;

/**
 * Created by rom on 13/03/2016.
 */
public class QuGcmListenerService extends GcmListenerService {
    private static final String TAG = "QuGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */

    @Override
    public void onMessageReceived(String from, Bundle data) {

        String message = data.getString("messageText");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        //if (from.startsWith("/topics/")) {
            // message received from some topic.
        //} else {
            // normal downstream message.
        //}

        Log.d(TAG, "onMessageReceived");

        String messageText = data.getString("messageText");
        Log.d(TAG, "messageText: " + messageText);
        String invitationId = data.getString("invitationId");
        Log.d(TAG, "invitationId: " + invitationId);
        String gameId = data.getString("gameId");
        Log.d(TAG, "gameId      : " + gameId);
        String playerId = data.getString("playerId");
        Log.d(TAG, "playerId    : " + playerId);
        String nickName = data.getString("nickName");
        Log.d(TAG, "nickName    : " + nickName);

        // Get an instance of the application to determine if the application is
        // running in the foreground and if a game is currently being played
        QuizupApplication app = (QuizupApplication) getApplication();
        ApplicationSettings settings = app.getApplicationSettings();

        boolean currentPlayerIsInvitee = playerId.equalsIgnoreCase(settings.getPlayerId());
        boolean isCurrentlyPlayingGame = app.getIsCurrentlyPlayingGame();
        boolean isApplicationActive = app.getIsApplicationActive();

        // ensure that the currently logged in player is the player
        // that is intended to receive the invitation or that the application is in the background.
        if (!isCurrentlyPlayingGame && (currentPlayerIsInvitee || !isApplicationActive)) {
            ApplicationNotifications.generateInvitationNotification(this.getBaseContext(),
                    messageText,
                    gameId,
                    invitationId,
                    playerId,
                    nickName);
        }

    }
}
