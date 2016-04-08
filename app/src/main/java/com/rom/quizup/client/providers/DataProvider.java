package com.rom.quizup.client.providers;

import android.content.Context;

import java.util.List;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.R;
import com.rom.quizup.client.models.GamePlayStatus;
import com.rom.quizup.client.services.Quizup;


/**
 * The purpose of this class is to support making asynchronous calls to the app engine cloud
 * endpoints
 */
public class DataProvider {

  private Quizup service = null;

  private ApplicationSettings settings = null;

  /**
   * Constructor
   *
   * @param context The context
   */
  public DataProvider(Context context) {
    settings = new ApplicationSettings(context);

    service = Quizup.INSTANCE;
  }

  /**
   * Register a player
   *
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void registerPlayer(OnRegisterPlayerCompleted listener) {
    RegisterPlayerTask task = new RegisterPlayerTask(service, settings, listener);
    task.execute();
  }

  /**
   * Unregister the device
   *
   * @param deviceId The device ID
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void unRegisterDevice(String deviceId, OnUnregisterDeviceCompleted listener) {
    UnregisterDeviceTask task = new UnregisterDeviceTask(service, settings, deviceId, listener);
    task.execute();
  }

  /**
   * Register a device
   *
   * @param deviceId The device ID
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void registerDevice(String deviceId, OnRegisterDeviceCompleted listener) {
    RegisterDeviceTask task = new RegisterDeviceTask(service, settings, deviceId, listener);
    task.execute();
  }

  /**
   * Send an invitation to a multiplayer game
   *
   * @param playerId The player ID to invite
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void sendInvitation(String playerId, OnSendInvitationCompleted listener) {
    SendInvitationTask task = new SendInvitationTask(service, settings, playerId, listener);
    task.execute();
  }

  /**
   * Send an invitation to a multiplayer game
   *
   * @param plusId The player's Google+ ID
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  //public void sendInvitation(String plusId, OnSendInvitationCompleted listener) {
  // SendInvitationByPlusIdTask task = new SendInvitationByPlusIdTask(service, plusId, listener);
  //  task.execute();
  //}

  /**
   * Accept an invitation
   *
   * @param gameId The game ID
   * @param invitationId The invitation ID
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void acceptInvitation(String gameId, String invitationId, OnAcceptInvitationCompleted listener) {
    AcceptInvitationTask task = new AcceptInvitationTask(service, settings, gameId, invitationId, listener);
    task.execute();
  }

  /**
   * Decline an invitation
   *
   * @param gameId The game ID
   * @param invitationId The invitation ID
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void declineInvitation(
      String gameId, String invitationId, OnDeclineInvitationCompleted listener) {
    DeclineInvitationTask task = new DeclineInvitationTask(service, settings, gameId, invitationId, listener);
    task.execute();
  }

  /**
   * Get the opponents response to an invitation
   *
   * @param gameId The game ID
   * @param invitationId The invitation ID
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void getOpponentResponseToInvitation(
      String gameId, String invitationId, OnGetOpponentResponseToInvitationCompleted listener) {
    GetOpponentResponseToInvitationTask task =
        new GetOpponentResponseToInvitationTask(service, settings, gameId, invitationId, listener);
    task.execute();
  }

  /**
   * Get the list of active registered players.
   *
   *  This method was intentionally simplified for a small number of opponents. If large lists are
   * expected to be returned paging should be implemented.
   *
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void getPlayerList(OnGetPlayerListCompleted listener) {
    GetPlayerListTask task = new GetPlayerListTask(service, settings, listener);
    task.execute();
  }

  /**
   * Get a game
   *
   * @param gameId The game ID
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void getGame(String gameId, OnGetGameCompleted listener) {
    GetGameTask task = new GetGameTask(service, settings, gameId, listener);
    task.execute();
  }

  /**
   * Start a new single player game
   *
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void startNewSinglePlayerGame(OnStartNewSinglePlayerGameCompleted listener) {
    StartNewSinglePlayerGameTask task = new StartNewSinglePlayerGameTask(service, settings, listener);
    task.execute();
  }

  /**
   * Cancel an invitation
   *
   * @param gameId The game ID
   * @param invitationId The invitation ID
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void cancelInvitation(
      String gameId, String invitationId, OnCancelInvitationCompleted listener) {
    CancelInvitationTask task = new CancelInvitationTask(service, settings, gameId, invitationId, listener);
    task.execute();
  }

  /**
   * Get the current player's record
   *
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void getPlayerRecord(OnGetPlayerRecordCompleted listener) {
    GetPlayerRecordTask task = new GetPlayerRecordTask(service, settings, listener);
    task.execute();
  }

  /**
   * Submit the current player's answers for a game
   *
   * @param gameId The game ID
   * @param answers The list of indexes of the questions answered
   * @param timeLeft The amount of time left on the game board in milliseconds
   * @param listener The listener that is executed when the asynchronous call is complete
   */
  public void submitAnswers(
      String gameId, List<Integer> answers, Long timeLeft, OnSubmitAnswersCompleted listener) {
    GamePlayStatus playerAnswers = new GamePlayStatus(answers, timeLeft);

    SubmitAnswersTask task = new SubmitAnswersTask(service, settings, gameId, playerAnswers, listener);
    task.execute();
  }
}
