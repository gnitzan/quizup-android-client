package com.rom.quizup.client.providers;


import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.Player;
import com.rom.quizup.client.models.QuPlayer;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to register the player
 *
 */
public class RegisterPlayerTask extends ServiceTask<Player> {

  private ApplicationSettings settings = null;
  private OnRegisterPlayerCompleted listener;

  public RegisterPlayerTask(Quizup service, ApplicationSettings settings, OnRegisterPlayerCompleted listener) {
    super(service);
    this.settings = settings;
    this.listener = listener;
  }

  @Override
  protected void onPostExecute(Player player) {

    QuPlayer model = null;

    if (player != null) {
      settings.setPlayerId(player.getId());
      model = new QuPlayer(player);
    }

    listener.onRegisterPlayerCompleted(model);
  }

  @Override
  protected Player executeEndpointCall(String... params) throws IOException {

    Call<Player> call = service.getPlayerServices().registerUser(settings.getAuthToken());

    return call.execute().body();
  }
}
