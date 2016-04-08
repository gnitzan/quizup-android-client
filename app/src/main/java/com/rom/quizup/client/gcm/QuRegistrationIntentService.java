package com.rom.quizup.client.gcm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.QuizupApplication;
import com.rom.quizup.client.R;
import com.rom.quizup.client.providers.DataProvider;
import com.rom.quizup.client.providers.OnRegisterDeviceCompleted;
import com.rom.quizup.client.providers.OnUnregisterDeviceCompleted;

import java.io.IOException;
import java.util.Map;

/**
 * Handles registration with GCM and send QuizUp server the instance' token.
 * Subscribe to the application GCM topics.
 *
 * Created by rom on 13/03/2016.
 */
public class QuRegistrationIntentService extends IntentService implements OnRegisterDeviceCompleted {

    private static final String TAG = "RegIntentService";

    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    private DataProvider dataProvider = null;
    private ApplicationSettings applicationSettings = null;

    // channels available
    private static final String[] TOPICS = {"global"};

    /**
     * Constructor
     */
    public QuRegistrationIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        QuizupApplication application = (QuizupApplication)getApplication();

        dataProvider = application.getDataProvider();

        applicationSettings =application.getApplicationSettings();

        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = null;
        try {
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            InstanceID instanceID = InstanceID.getInstance(this);
            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, "GCM Registration Token: " + token);

            //if (applicationSettings.getGcmTokenSent() == null) {
                applicationSettings.setGcmTokenSent(token);
                dataProvider.registerDevice(token, this);
            //}
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);

            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            applicationSettings.setGcmTokenSent(null);
        }
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    private void subscribeTopics(final String token) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                GcmPubSub pubSub = GcmPubSub.getInstance(QuRegistrationIntentService.this);
                for (String topic : TOPICS) {
                    try {
                        pubSub.subscribe(token, "/topics/" + topic, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                        //throw new RuntimeException(e);
                    }
                }
            }
        }).start();

    }

    @Override
    public void onRegisterDeviceCompleted(Map<String, Boolean> result) {
        if (result != null && result.get("success")) {
            // Subscribe to topic channels
            try {
                subscribeTopics(applicationSettings.getGcmTokenSent());
            } catch (IOException e) {
                applicationSettings.setGcmTokenSent(null);
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            applicationSettings.setGcmTokenSent(null);
        }
    }
}
