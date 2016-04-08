package com.rom.quizup.client;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.rom.quizup.client.providers.DataProvider;
import com.rom.quizup.client.services.Quizup;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * global application state QuizupApplication app = (QuizupApplication)getApplication();
 */
public class QuizupApplication extends Application {

  static final String TAG = QuizupApplication.class.getSimpleName();

  private GoogleSignInAccount mAccount = null;

  private GoogleApiClient mGoogleApiClient = null;

  private ApplicationSettings mSettings = null;

  private DataProvider mDataProvider = null;

  private boolean isApplicationActive = false;

  private boolean isCurrentlyPlayingGame = false;

  private void init() {
    Log.d(TAG, "initializing application components");

    // to prevent EOFException after idle
    // http://code.google.com/p/google-http-java-client/issues/detail?id=116
    System.setProperty("http.keepAlive", "false");

    mSettings = new ApplicationSettings(this.getApplicationContext());
  }

  /**
   * Sets the application as active
   */
  public void setApplicationActive() {
    isApplicationActive = true;
  }

  /**
   * Sets the application as inactive
   */
  public void setApplicationInactive() {
    isApplicationActive = false;
  }

  /**
   * Determines if the application is active
   *
   * @return Returns <code>true</code> if the application is active
   */
  public boolean getIsApplicationActive() {
    return isApplicationActive;
  }

  /**
   * Sets whether or not the user is currently playing a game
   *
   * @param playingGame
   */
  public void setIsCurrentlyPlayingGame(boolean playingGame) {
    this.isCurrentlyPlayingGame = playingGame;
  }

  /**
   * Gets whether or not the user is playing a game
   *
   * @return Returns <code>true</code> if the user is playing a game
   */
  public boolean getIsCurrentlyPlayingGame() {
    return isCurrentlyPlayingGame;
  }

  /**
   * Initializes state of the currently selected account and also initializes the data provider
   */
  public void initializeGoogleServices() {
    initializeDataProvider();
  }

  /**
   * Gets an instance of the data provider
   *
   * @return {@link DataProvider}
   */
  public DataProvider getDataProvider() {
    if ( this.mDataProvider == null ) {
      initializeDataProvider();
    }

    return  this.mDataProvider;
  }

  /**
   * Get whether or not the user is signed in
   *
   * @return Returns <code>true</code> if the user is signed in
   */
  public boolean isUserSignedIn() {
    return mSettings.isUserSignedIn();
  }

  /**
   * Sets the user as signed in based upon the value passed
   *
   * @param value The value indicating if the user is signed in
   */
  public void setSignedIn(boolean value) {
    mSettings.setSignedIn(value);
  }

  /**
   * Gets the Google account currently configured in shared preferences, or null if not set
   *
   * @return {@link String}
   */
  public String getSelectedAccountName() {
    return mSettings.getSelectedAccountName();
  }

  private void initializeDataProvider() {
    //if (this.isUserSignedIn()) {
      mDataProvider = new DataProvider(this);
    //}
  }

  /**
   * Gets the application name and version
   *
   * @return {@link String}
   */
  public String getAppNameAndVersion() {
    try {
      PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);

      return String.format("%s v%s", getString(R.string.app_name),
              // packageInfo.versionCode,
              packageInfo.versionName);
    } catch (NameNotFoundException e) {
      // should never happen
      throw new RuntimeException("Coult not get package name: " + e);
    }
  }

  /**
   * Provides the ability to set the selected account in the shared preferences, marks the user as
   * signed in if the value isn't null and assigns the selected account for the
   * {@link GoogleAccountCredential}
   *
   * @param accountName The selected account name
   */
  public void setSelectedAccountName(String accountName) {
    // new
    mSettings.setAccountName(accountName);
    mSettings.setSignedIn(accountName != null);
  }

  /**
   * Set the previously selected account name
   *
   * @param accountName The previously selected account name
   */
  public void setPreviousAccount(String accountName) {
    mSettings.setPreviousAccountName(accountName);
  }

  /**
   * Gets the previously selected account name
   *
   * @return {@link String}
   */
  public String getPreviousAccount() {
    return mSettings.getPreviousAccountName();
  }

  /**
   * Returns the current player's ID
   *
   * @return {@link Long}
   */
  public String getCurrentPlayerId() {
    return mSettings.getPlayerId();
  }

  /**
   * Returns an instance of the application settings
   *
   * @return {@link ApplicationSettings}
   */
  public ApplicationSettings getApplicationSettings() {
    return mSettings;
  }

  /**
   * Called by the system when the device configuration changes while your component is running.
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    Log.d(TAG, "onConfigurationChanged()");
    super.onConfigurationChanged(newConfig);
  }

  /**
   * Called when the application is starting, before any activity, service, or receiver objects
   * (excluding content providers) have been created
   */
  @Override
  public void onCreate() {
    Log.d(TAG, "onCreate");
    super.onCreate();
    init();
  }

  /**
   * This is called when the overall system is running low on memory, and would like actively
   * running process to try to tighten their belt. Applications that want to be nice can implement
   * this method to release any caches or other unnecessary resources they may be holding on to. The
   * system will perform a gc for you after returning from this method.
   */
  @Override
  public void onLowMemory() {
    Log.d(TAG, "onLowMemory()");
    super.onLowMemory();
  }

  /**
   * This method is for use in emulated process environments. It will never be called on a
   * production Android device, where processes are removed by simply killing them; no user code
   * (including this callback) is executed when doing so
   */
  @Override
  public void onTerminate() {
    Log.d(TAG, "onTerminate()");
    super.onTerminate();
  }

  public GoogleApiClient getGoogleApiClient() {
    return mGoogleApiClient;
  }

  public void setGoogleApiClient(GoogleApiClient mGoogleApiClient) {
    this.mGoogleApiClient = mGoogleApiClient;
  }

  public void setAccount(GoogleSignInAccount account) {
    mAccount = account;
  }

  public GoogleSignInAccount getAccount() {
    return mAccount;
  }
}