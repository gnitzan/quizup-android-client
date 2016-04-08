package com.rom.quizup.client.providers;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.Player;
import com.rom.quizup.client.models.QuPlayer;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to retrieve a player's multiplayer
 * record
 *
 */
public class GetPlayerRecordTask extends ServiceTask<Player> {

  private OnGetPlayerRecordCompleted listener;
  private ApplicationSettings settings;

  public GetPlayerRecordTask(Quizup service, ApplicationSettings settings, OnGetPlayerRecordCompleted listener) {
    super(service);
    this.listener = listener;
    this.settings = settings;
  }

  @Override
  protected void onPostExecute(Player player) {

    QuPlayer model = null;

    if (player != null) {
      model = new QuPlayer(player.getId(), player.getNickname(), player.getStatistics().getNumberOfWins(),
          player.getStatistics().getNumberOfGames());
    }

    listener.onGetPlayerRecordCompleted(model);
  }

  @Override
  protected Player executeEndpointCall(String... params) throws IOException {
    Call<Player> call = service.getPlayerServices().getPlayer(settings.getAuthToken());

    return call.execute().body();
  }
}
