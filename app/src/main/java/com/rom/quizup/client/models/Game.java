package com.rom.quizup.client.models;

import java.io.Serializable;
import java.util.List;

/**
 * Game resource. This class is used in the Quizup services and is
 * projected to the clients in the generated client libraries.
 */
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Board board;
    private List<GamePlay> gamePlays;

    /**
     * Constructor
     *
     * @param id
     *          the id of the game.
     * @param board
     *          the board used in this game.
     * @param gamePlays
     *          the list of game plays from each player in this game.
     */
    public Game(String id, Board board, List<GamePlay> gamePlays) {
        this.id = id;
        this.board = board;
        this.gamePlays = gamePlays;
    }

    /**
     * Gets the game id.
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the board used for this game.
     *
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the list of game plays from each player in this game.
     *
     */
    public List<GamePlay> getGamePlays() {
        return gamePlays;
    }
}