package com.rom.quizup.client.ui.game.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.rom.quizup.client.helpers.BoundedGrid;
import com.rom.quizup.client.helpers.Location;

/**
 * The purpose of this class is to manage the selected letters from the game board including such
 * functionality as selection validation.
 */
public class GridLayoutRenderModel {
  private BoundedGrid<LetterRenderModel> board;
  Queue<LetterRenderModel> selectedLetterQueue = new LinkedList<LetterRenderModel>();

  /**
   * Return the currently selected letters
   *
   * @return {@link String}
   */
  public String getSelectedWord() {
    String word = "";
    for (LetterRenderModel rlm : selectedLetterQueue) {
      word += rlm.getGlyph();
    }
    return word;
  }

  /**
   * Get the game board
   *
   * @return {@link BoundedGrid}&lt;{@link LetterRenderModel}&gt;
   */
  public BoundedGrid<LetterRenderModel> getBoard() {
    return board;
  }

  /**
   * Set the game board
   *
   * @param board The game board
   */
  public void setBoard(BoundedGrid<LetterRenderModel> board) {
    this.board = board;
  }

  private Boolean validateConnectedness(Location loc, int direction) {
    Boolean result = false;
    Location targetLocation = loc.getAdjacentLocation(direction);
    if (board.isValid(loc.getAdjacentLocation(direction))) {
      return board.get(targetLocation).getIsSelected();
    }
    return result;
  }

  /**
   * Toggles the letter's selected state
   *
   * @param loc The location of the letter
   */
  public void toggleLetterSelection(Location loc) {
    if (board.isValid(loc)) {
      LetterRenderModel letter = board.get(loc);
      if (letter.getIsSelected()) {
        selectedLetterQueue.remove(letter);
      } else {
        selectedLetterQueue.add(letter);
      }
      letter.setIsSelected(!letter.getIsSelected());
    }
  }

  /**
   * Returns <code>true</code> if the selected letters adhere to the rules of the game board.
   *
   * @return {@link Boolean}
   */
  public Boolean validateConnectedness() {
    if (this.getSelectedLetters().length > 1) {
      for (Location loc : this.getSelectedLetters()) {
        if (!(validateConnectedness(loc, Location.NORTH)
            || validateConnectedness(loc, Location.EAST)
            || validateConnectedness(loc, Location.SOUTH)
            || validateConnectedness(loc, Location.WEST)
            || validateConnectedness(loc, Location.NORTHEAST)
            || validateConnectedness(loc, Location.SOUTHEAST)
            || validateConnectedness(loc, Location.SOUTHWEST)
            || validateConnectedness(loc, Location.NORTHWEST))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns the list of selected letters
   *
   * @return {@link Queue}&lt;{@link LetterRenderModel}&gt;
   */
  public Queue<LetterRenderModel> getSelectedLetterQueue() {
    return selectedLetterQueue;
  }

  /**
   * Clears the selected letters
   */
  public void clearSelectedLetters() {
    for (int r = 0; r <= this.board.getNumRows() - 1; r++) {
      for (int c = 0; c <= this.board.getNumCols() - 1; c++) {
        Location letterLocation = new Location(c, r);
        board.get(letterLocation).setIsSelected(false);
      }
    }
    selectedLetterQueue.clear();
  }

  /**
   * Returns the locations of the selected letters
   *
   * @return {@link Location}[]
   */
  public Location[] getSelectedLetters() {
    ArrayList<Location> results = new ArrayList<Location>();

    for (int r = 0; r <= this.board.getNumRows() - 1; r++) {
      for (int c = 0; c <= this.board.getNumCols() - 1; c++) {
        Location letterLocation = new Location(c, r);
        LetterRenderModel letter = board.get(letterLocation);
        if (letter.getIsSelected()) {
          results.add(letterLocation);
        }
      }
    }

    return results.toArray(new Location[results.size()]);
  }
}
