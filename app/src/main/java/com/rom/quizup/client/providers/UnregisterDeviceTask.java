package com.rom.quizup.client.providers;


import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.User;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to unregister a device for a specific
 * player
 *
 */
public class UnregisterDeviceTask extends ServiceTask<Map<String, Boolean>> {
  private OnUnregisterDeviceCompleted listener;
  private ApplicationSettings settings;
  private String deviceId;

  public UnregisterDeviceTask(Quizup service, ApplicationSettings settings, String deviceId, OnUnregisterDeviceCompleted listener) {
    super(service);
    this.listener = listener;
    this.deviceId = deviceId;
    this.settings = settings;
  }

  @Override
  protected void onPostExecute(Map<String, Boolean> map) {
    listener.onUnregisterDeviceCompleted(map);
  }

  @Override
  protected Map<String, Boolean> executeEndpointCall(String... params) throws IOException {

    Call<Map<String, Boolean>> call = service.getPlayerServices().unregisterDevice(deviceId, settings.getAuthToken());

    return call.execute().body();
  }
}
