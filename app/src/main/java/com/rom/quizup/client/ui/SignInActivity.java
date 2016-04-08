package com.rom.quizup.client.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.QuizupApplication;
import com.rom.quizup.client.R;
import com.rom.quizup.client.gcm.QuRegistrationIntentService;
import com.rom.quizup.client.models.QuPlayer;
import com.rom.quizup.client.providers.DataProvider;
import com.rom.quizup.client.providers.OnRegisterPlayerCompleted;
import com.rom.quizup.client.providers.RegisterPlayerTask;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, OnRegisterPlayerCompleted {
    private static final String TAG = SignInActivity.class.getSimpleName();
    private QuizupApplication mApplication;
    private DataProvider dataProvider;
    private static final int RC_GET_TOKEN = 9002;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // set our view
        setContentView(R.layout.activity_sign_in);

        mApplication = (QuizupApplication)getApplication();

        dataProvider = mApplication.getDataProvider();

        // Button click listeners
        findViewById(com.rom.quizup.client.R.id.sign_in_button).setOnClickListener(this);
        findViewById(com.rom.quizup.client.R.id.sign_out_button).setOnClickListener(this);
        findViewById(com.rom.quizup.client.R.id.disconnect_button).setOnClickListener(this);

        // Request only the user's ID token, which can be used to identify the
        // user securely to your backend. This will contain the user's basic
        // profile (name, profile picture URL, etc) so you should not need to
        // make an additional call to personalize your application.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.rom.quizup.client.R.string.server_client_id))
                .requestEmail()
                .build();

        // Build GoogleAPIClient with the Google Sign-In API and the above options.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                /*.addApi(Plus.API)*/
                .build();
    }

    private void signIn() {
        // Show an account picker to let the user choose a Google account from the device.
        // Since QuizUp GoogleSignInOptions only asks for IDToken then no
        // consent screen will be shown here.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d(TAG, "signOut:onResult:" + status);
                        updateUI(false);
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d(TAG, "revokeAccess:onResult:" + status);
                        updateUI(false);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_TOKEN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d(TAG, "onActivityResult:GET_TOKEN:success:" + result.getStatus().isSuccess());

            if (result.isSuccess()) {

                mApplication.setGoogleApiClient(mGoogleApiClient);

                mApplication.setAccount(result.getSignInAccount());

                String idToken = mApplication.getAccount().getIdToken();

                mApplication.setSelectedAccountName(mApplication.getAccount().getEmail());

                // save our token to app settings
                mApplication.getApplicationSettings().setAuthToken(idToken);

                // send token to server and validate server-side
                dataProvider.registerPlayer(this);

            } else {
                // Show signed-out UI.
                updateUI(false);
            }
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            ((TextView) findViewById(com.rom.quizup.client.R.id.status)).setText(com.rom.quizup.client.R.string.signed_in);

            findViewById(com.rom.quizup.client.R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(com.rom.quizup.client.R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            ((TextView) findViewById(com.rom.quizup.client.R.id.status)).setText(com.rom.quizup.client.R.string.signed_out);

            findViewById(com.rom.quizup.client.R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(com.rom.quizup.client.R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.rom.quizup.client.R.id.sign_in_button:
                signIn();
                break;
            case com.rom.quizup.client.R.id.sign_out_button:
                signOut();
                break;
            case com.rom.quizup.client.R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onRegisterPlayerCompleted(QuPlayer player) {
        if (player != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(QuPlayer.TAG, player);
            startActivity(intent);

            // Start IntentService to register this application with GCM.
            Intent gcmIntent = new Intent(this, QuRegistrationIntentService.class);
            startService(gcmIntent);

            this.finish();
        }
        else {
            signOut();
        }
    }
}
