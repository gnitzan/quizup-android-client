package com.rom.quizup.client.models;

import java.io.Serializable;
import java.util.List;

/**
 * Representation of one player's participation in a game. This class is used in
 * the Quizup services and is projected to the server
 */
public class GamePlay implements Serializable {
    private static final long serialVersionUID = 1L;
    private Player player;
    private List<Integer> correctAnswers;
    private boolean finished;
    private long timeLeft;
    private boolean isWinner;

    /**
     * Constructor
     *
     * @param player
     *          the player who played a game.
     * @param correctAnswers
     *          the list of player's correct answers.
     * @param finished
     *          true if the player finished the game and submitted the answers;
     *          false otherwise.
     * @param timeLeft
     *          time in milliseconds still left when the player submitted the
     *          answers.
     * @param isWinner
     *          true if this player is the winner of the game; false otherwise.
     */
    public GamePlay(Player player, List<Integer> correctAnswers, boolean finished, long timeLeft, boolean isWinner) {
        this.player = player;
        this.correctAnswers = correctAnswers;
        this.finished = finished;
        this.timeLeft = timeLeft;
        this.isWinner = isWinner;
    }

    /**
     * Gets the player resource.
     *
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the list of correct answers.
     *
     */
    public List<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Gets the flag that indicates whether the player finished the game.
     *
     * @return true if the player finished the game and submitted the answers;
     *         false otherwise
     */
    public boolean getFinished() {
        return finished;
    }

    /**
     * Gets the time in milliseconds still left when the player submitted the
     * answers.
     *
     */
    public long getTimeLeft() {
        return timeLeft;
    }

    /**
     * Gets the flag that indicates whether the player won the game.
     *
     * @return true if this player is the winner of the game; false otherwise.
     */
    public boolean getIsWinner() {
        return isWinner;
    }
}
