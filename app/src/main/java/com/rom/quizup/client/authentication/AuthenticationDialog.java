package com.rom.quizup.client.authentication;

/**
 * The purpose of this enum is for categorizing the type of authentication dialog
 */
public enum AuthenticationDialog {
    AUTHORIZATION("User authorization", 2),
    CHOOSE_ACCOUNT("Choose an account", 1),
    GOOGLE_PLAY_SERVICES("Google Play services", 0);

    private int intValue;
    private String stringValue;

    private AuthenticationDialog(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    /**
     * Returns the {@link String} value of the enum
     */
    @Override
    public String toString() {
        return stringValue;
    }

    /**
     * Returns the integer value of the enum
     *
     * @return The integer value of the enum
     */
    public int value() {
        return intValue;
    }
}