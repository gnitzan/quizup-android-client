package com.rom.quizup.client.providers;


import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.User;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;
import java.util.Map;

import retrofit2.Call;

/**
 * The purpose of this class is to make an asynchronous call to register the device
 *
 */
public class RegisterDeviceTask extends ServiceTask<Map<String, Boolean>> {
  private OnRegisterDeviceCompleted listener;
  private String deviceId = null;
  private ApplicationSettings settings;

  public RegisterDeviceTask(Quizup service, ApplicationSettings settings, String deviceId, OnRegisterDeviceCompleted listener) {
    super(service);
    this.deviceId = deviceId;
    this.listener = listener;
    this.settings = settings;
  }

  @Override
  protected void onPostExecute(Map<String, Boolean> map) {
    listener.onRegisterDeviceCompleted(map);
  }

  @Override
  protected Map<String, Boolean> executeEndpointCall(String... params) throws IOException {

    Call<Map<String, Boolean>> call = service.getPlayerServices().registerDevice(deviceId, 1, settings.getAuthToken());

    return call.execute().body();
  }
}
