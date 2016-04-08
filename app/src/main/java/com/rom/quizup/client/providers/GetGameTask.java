package com.rom.quizup.client.providers;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.Game;
import com.rom.quizup.client.models.QuGame;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to get a game
 *
 */
public class GetGameTask extends ServiceTask<Game> {

  private ApplicationSettings settings = null;
  private OnGetGameCompleted listener;
  private String gameId;

  public GetGameTask(
      Quizup service, ApplicationSettings settings, String gameId, OnGetGameCompleted listener) {
    super(service);
    this.settings = settings;
    this.listener = listener;
    this.gameId = gameId;
  }

  @Override
  protected void onPostExecute(Game game) {

    QuGame model = null;

    if (game != null && game.getId() != null) {
      model = GameBuilder.buildGameModel(settings, game);
    }

    listener.onGetGameCompleted(model);
  }

  @Override
  protected Game executeEndpointCall(String... params) throws IOException {

    Call<Game> call = service.getGameServices().getGame(gameId, settings.getAuthToken());

    return call.execute().body();
  }
}
