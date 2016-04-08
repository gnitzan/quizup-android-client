package com.rom.quizup.client.models;

import java.util.List;

/**
 * Class representing the status of a game play.
 */
public class GamePlayStatus {
    private List<Integer> correctAnswers;
    private long timeLeft;

    /**
     * Default constructor needed to instantiate an object from Json passed
     */
    public GamePlayStatus() {}

    /**
     * Constructor.
     *
     * @param correctAnswers the list of player's correct answers.
     * @param timeLeft time in milliseconds still left when the player submitted the answers.
     */
    public GamePlayStatus(List<Integer> correctAnswers, long timeLeft) {
        this.correctAnswers = correctAnswers;
        this.timeLeft = timeLeft;
    }

    /**
     * Gets the list of correct answers.
     *
     */
    public List<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Gets the time in milliseconds still left when the player submitted the answers.
     *
     */
    public long getTimeLeft() {
        return timeLeft;
    }
}