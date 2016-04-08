package com.rom.quizup.client.providers;

import android.nfc.Tag;
import android.util.Log;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.Game;
import com.rom.quizup.client.models.QuGame;
import com.rom.quizup.client.models.User;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to get a game and board model to start
 * a new single player game
 *
 */
public class StartNewSinglePlayerGameTask extends ServiceTask<Game> {
  private ApplicationSettings settings = null;
  private OnStartNewSinglePlayerGameCompleted listener;
  private static final String TAG = StartNewSinglePlayerGameTask.class.getSimpleName();

  public StartNewSinglePlayerGameTask(Quizup service, ApplicationSettings settings, OnStartNewSinglePlayerGameCompleted listener) {
    super(service);
    this.settings = settings;
    this.listener = listener;
  }

  @Override
  protected void onPostExecute(Game game) {
    Log.i(TAG, "onPostExecute");

    QuGame model = null;

    if (game != null && game.getId() != null) {
      model = GameBuilder.buildGameModel(settings, game);
    }

    listener.onStartNewSinglePlayerGameCompleted(model);
  }

  @Override
  protected Game executeEndpointCall(String... params) throws IOException {
    //this.service = Quizup.INSTANCE.reload();
    User user = new User(settings.getAuthToken());
    user.setUsername(settings.getSelectedAccountName());
    user.setEmail(settings.getSelectedAccountName());

    Call<Game> call = service.getGameServices().startSinglePlayerGame(1, user);

    Log.i(TAG, "calling server startMulti");
    Game game = call.execute().body();

    return game;
  }

}
