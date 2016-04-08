package com.rom.quizup.client.authentication;

/**
 * Listener interface for events relating to user authentication
 */
public interface AuthEventListener {

    /**
     * Called when authentication has successfully completed
     */
    void onAuthCompleted();

    /**
     * Called when the authentication failed or user canceled
     */
    void onAuthFailed();
}