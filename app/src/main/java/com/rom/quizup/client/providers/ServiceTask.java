package com.rom.quizup.client.providers;


import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.rom.quizup.client.services.Quizup;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * The purpose of this class is to provide a generic method of performing asynchronous calls to our server's endpoints
 *
 * @param <T>
 */
public abstract class ServiceTask<T> extends AsyncTask<String, Void, T> {

  protected Quizup service = null;
  private static final int RETRY_MAX = 0;
  private static final String TAG = ServiceTask.class.getSimpleName();

  protected ServiceTask(Quizup service) {
    this.service = service;
  }

  @Override
  protected T doInBackground(String... params) {
    int attemptNumber = 0;

    while (attemptNumber <= RETRY_MAX) {
      try {

        attemptNumber++;

        return executeEndpointCall(params);
      } catch (java.net.SocketTimeoutException e) {
        Log.e(TAG, "ServiceTask<T> doInBackground", e);
      } catch (GoogleJsonResponseException e) {
        Log.e(TAG, "ServiceTask<T> doInBackground", e);
        // Do not retry on HTTP 4xx errors
        if (e.getStatusCode() >= 400 && e.getStatusCode() <= 499) {
          return null;
        }
      } catch (IOException e) {
        Log.e(TAG, "ServiceTask<T> doInBackground", e);
      } catch (RuntimeException e) {
        Log.e(TAG, "ServiceTask<T> doInBackground", e);
      }
    }

    return null;
  }

  protected abstract T executeEndpointCall(String... params) throws IOException;
}
