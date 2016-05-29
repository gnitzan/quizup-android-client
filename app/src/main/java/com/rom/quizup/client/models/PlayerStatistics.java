package com.rom.quizup.client.models;

/**
 * Player statistics. This class is used in the QuizUp API and
 * is projected to the clients in the generated client libraries.
 */

public class PlayerStatistics {
    private int numberOfWins = 0;
    private int numberOfGames = 0;

    /**
     * Constructor
     *
     * @param numberOfWins
     *          number of wins in multiplayer games.
     * @param numberOfGames
     *          number of multiplayer games played.
     */
    public PlayerStatistics(int numberOfWins, int numberOfGames) {
        this.numberOfWins = numberOfWins;
        this.numberOfGames = numberOfGames;
    }

    /**
     * Gets the number of multiplayer games won.
     *
     */
    public int getNumberOfWins() {
        return numberOfWins;
    }

    /**
     * Gets the number of multiplayer games played.
     *
     */
    public int getNumberOfGames() {
        return numberOfGames;
    }
}