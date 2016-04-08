package com.rom.quizup.client.providers;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.Player;
import com.rom.quizup.client.models.PlayerCollection;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to get a list of active players
 *
 */
public class GetPlayerListTask extends ServiceTask<List<Player>> {

  private OnGetPlayerListCompleted listener;
  private ApplicationSettings settings;

  public GetPlayerListTask(Quizup service, ApplicationSettings settings, OnGetPlayerListCompleted listener) {
    super(service);
    this.listener = listener;
    this.settings = settings;
  }

  @Override
  protected void onPostExecute(List<Player> result) {

    listener.onGetPlayerListCompleted(result);
  }

  @Override
  protected List<Player> executeEndpointCall(String... params) throws IOException {
    Call<List<Player>> call = service.getPlayerServices().getPlayerList(settings.getAuthToken());

    return call.execute().body();
  }
}
