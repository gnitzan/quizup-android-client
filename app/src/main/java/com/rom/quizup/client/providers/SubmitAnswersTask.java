package com.rom.quizup.client.providers;

import android.util.Log;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.GamePlayStatus;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to submit answers for a specific game
 *
 */
public class SubmitAnswersTask extends ServiceTask<Map<String, Boolean>> {
  private static String TAG = SubmitAnswersTask.class.getSimpleName();

  private OnSubmitAnswersCompleted listener;
  private String gameId;
  private GamePlayStatus answers;
  private ApplicationSettings settings;

  public SubmitAnswersTask(Quizup service, ApplicationSettings settings, String gameId, GamePlayStatus answers, OnSubmitAnswersCompleted listener) {
    super(service);
    this.listener = listener;
    this.gameId = gameId;
    this.answers = answers;
    this.settings = settings;
  }

  @Override
  protected void onPostExecute(Map<String, Boolean> result) {
    super.onPostExecute(result);
    listener.onSubmitAnswersCompleted(result.get("success"));
  }

  @Override
  protected Map<String, Boolean> executeEndpointCall(String... params) throws IOException {
    Log.i(TAG, "submitAnswers for gameId: " + gameId + " with token: " + settings.getAuthToken());

    Call<Map<String, Boolean>> call = service.getGameServices().submitAnswers(gameId, answers, settings.getAuthToken());

    return call.execute().body();
  }
}
