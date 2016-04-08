package com.rom.quizup.client.providers;

import java.util.Map;

/**
 * The purpose of this interface is to be implemented by a class that serves as a listener to be
 * notified when a device has been registered.
 */
public interface OnRegisterDeviceCompleted {
  /**
   * Called when the device registration is completed
   *
   * @param result The result of the registration
   */
  void onRegisterDeviceCompleted(Map<String, Boolean> result);
}
