package com.rom.quizup.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/***
 * Handles application wide settings, an instance of this class can also be accessed through the
 * {@link QuizupApplication} class
 */
public class ApplicationSettings {
  private static String TAG = ApplicationSettings.class.getSimpleName();

  /**
   * Account name
   */
  public static final String PREF_ACCOUNT_NAME = "authAccount";

  /**
   * Flag GCM token was sent to server
   */
  public static final String PREF_SENT_GCM_TOKEN_TO_SERVER = "sentGcmTokenToServer";

  /**
   * Google Auth token
   */
  public static final String PREF_AUTH_TOKEN = "authToken";

  /**
   * Previous account name
   */
  public static final String PREF_PREV_ACCOUNT_NAME = "prevAuthAccount";

  /**
   * Signed In
   */
  public static final String PREF_SIGNED_IN = "isSignedIn";

  /**
   * Player ID
   */
  public static final String PREF_PLAYER_ID = "playerId";

  /**
   * Google +
   */
  public static final String PREF_GOOGLE_PLUS = "googlePlus";

  /**
   * Has ID been sent to cloud endpoint
   */
  public static final String PREF_HAS_PLUS_ID_BEEN_SET = "hasPlusIdBeenSet";

  /**
   * Instance of shared preferences
   */
  private SharedPreferences settings;

  /**
   * Constructor
   *
   * @param context The context
   */
  public ApplicationSettings(Context context) {
    settings = PreferenceManager.getDefaultSharedPreferences(context);
  }

  /**
   * Get the selcted account name
   *
   * @return {@link String}
   */
  public String getSelectedAccountName() {
    return settings.getString(PREF_ACCOUNT_NAME, null);
  }

  /**
   * Get the auth token
   *
   * @return {@link String}
   */
  public String getAuthToken() {
    return settings.getString(PREF_AUTH_TOKEN, null);
  }

  /**
   * Get GCM token sent to server
   *
   * @return {@link String}
   */
  public String getGcmTokenSent() {
    return settings.getString(PREF_SENT_GCM_TOKEN_TO_SERVER, null);
  }

  /**
   * Get the previously selected account name. This value may return null because it is normally
   * used during the account switch process
   *
   * @return {@link String}
   */
  public String getPreviousAccountName() {
    return settings.getString(PREF_PREV_ACCOUNT_NAME, null);
  }

  /**
   * Save the selected Google account into application's shared preferences
   *
   * @param accountName The account name
   */
  public void setAccountName(String accountName) {
    Log.i(TAG, "setAccountName: " + accountName);

    SharedPreferences.Editor editor = settings.edit();
    editor.putString(PREF_ACCOUNT_NAME, accountName);
    editor.commit();
  }

  /**
   * Save the flag GCM token sent to server into application's shared preferences
   *
   * @param sentGcmTokenToServer The account name
   */
  public void setGcmTokenSent(String sentGcmTokenToServer) {
    Log.i(TAG, "setGcmTokenSent: " + sentGcmTokenToServer);

    SharedPreferences.Editor editor = settings.edit();
    editor.putString(PREF_SENT_GCM_TOKEN_TO_SERVER, sentGcmTokenToServer);
    editor.commit();
  }

  /***
   * Get the playerId
   *
   * @return {@link Long}
   */
  public String getPlayerId() {
    return settings.getString(PREF_PLAYER_ID, null);
  }

  /**
   * Save the player ID into the application's shared preferences
   *
   * @param playerId The player's ID from the datastore
   */
  public void setPlayerId(String playerId) {
    Log.i(TAG, "setPlayerId: " + playerId);

    SharedPreferences.Editor editor = settings.edit();
    editor.putString(PREF_PLAYER_ID, playerId);
    editor.commit();
  }

  /**
   * Save the previously selected Google account into application's shared preferences. This is
   * typically used in the "Switch Account" functionality
   *
   * @param accountName The account name
   */
  public void setPreviousAccountName(String accountName) {
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(PREF_PREV_ACCOUNT_NAME, accountName);
    editor.commit();
  }

  /**
   * Sets the selected Google account signed status, when false a new authorization token will be
   * generated when need it
   *
   * @param value The signed in status
   */
  public void setSignedIn(boolean value) {
    Log.i(TAG, "setSignedIn: " + value);

    SharedPreferences.Editor editor = settings.edit();
    editor.putBoolean(PREF_SIGNED_IN, value);
    editor.commit();
  }

  /**
   * Sets the auth token
   *
   * @param value The auth token
   */
  public void setAuthToken(String value) {
    Log.i(TAG, "setAuthToken: " + value);

    SharedPreferences.Editor editor = settings.edit();
    editor.putString(PREF_AUTH_TOKEN, value);
    editor.commit();
  }

  /**
   * Whether the user is signed in or not, authentication process should be fire if this returns
   * false
   *
   * @return Returns <code>true</code> if the user is signed in
   */
  public boolean isUserSignedIn() {
    return settings.getBoolean(PREF_SIGNED_IN, false);
  }

  /**
   * Remove all preferences
   */
  public void clearPreferences() {
    SharedPreferences.Editor editor = settings.edit();
    editor.clear();
    editor.commit();
  }

  /**
   * Save the setting whether or not the current account is using Google+ within the application
   *
   * @param useGooglePlus The value that indicates if the user is using Google+
   */
  public void setUsingGooglePlus(boolean useGooglePlus) {
    Log.i(TAG, "setUsingGooglePlus: " + useGooglePlus);

    SharedPreferences.Editor editor = settings.edit();
    editor.putBoolean(PREF_GOOGLE_PLUS, useGooglePlus);
    editor.commit();
  }

  /**
   * Get the value indicating if the user is using Google+ in the application
   *
   * @return Returns <code>true</code> if the user is using Google+ in the application
   */
  public Boolean getUsingGooglePlus() {
    return settings.getBoolean(PREF_GOOGLE_PLUS, false);
  }

  /**
   * Set the value indicating that the plus ID has been sent to the cloud endpoints
   *
   * @param hasBeenSet The value indicating if the plus ID has been sent to the cloud endpoints
   */
  public void setHasPlusIdBeenSet(Boolean hasBeenSet) {
    Log.i(TAG, "setHasPlusIdBeenSet: " + hasBeenSet);

    SharedPreferences.Editor editor = settings.edit();
    editor.putBoolean(PREF_HAS_PLUS_ID_BEEN_SET, hasBeenSet);
    editor.commit();
  }

  /**
   * Get whether or not the user has had their plus ID sent to the cloud endpoints
   *
   * @return Returns <code>true</code> if the user has had their plus ID sent to the cloud endpoint
   */
  public Boolean getHasPlusIdBeenSet() {
    return settings.getBoolean(PREF_HAS_PLUS_ID_BEEN_SET, false);
  }
}
