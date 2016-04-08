package com.rom.quizup.client.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gal on 01/04/2016.
 */
public class PlayerCollection implements Serializable {

    private List<Player> players = new ArrayList<Player>();

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
