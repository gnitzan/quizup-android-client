package com.rom.quizup.client.helpers;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Represents a bounded grid data structure. A bounded grid can be considered an adaptable table
 *
 * @param <E>
 */
public class BoundedGrid<E> implements Iterator<E> {
  private Object[][] occupantArray;

  public BoundedGrid(int rows, int cols) {
    if (rows <= 0) {
      throw new IllegalArgumentException("rows <= 0");
    }
    if (cols <= 0) {
      throw new IllegalArgumentException("cols <= 0");
    }
    occupantArray = new Object[rows][cols];
  }

  /**
   * Get the number of rows
   *
   * @return The number of rows
   */
  public int getNumRows() {
    return occupantArray.length;
  }

  /**
   * Get the number of columns
   *
   * @return The number of columns
   */
  public int getNumCols() {
    return occupantArray[0].length;
  }

  /**
   * Determines if the location is valid
   *
   * @param loc The location on the grid
   * @return Returns <code>true</code> if the location is valid
   */
  public boolean isValid(Location loc) {
    return 0 <= loc.getRow() && loc.getRow() < getNumRows() && 0 <= loc.getCol()
        && loc.getCol() < getNumCols();
  }

  /**
   * Returns a list of locations that are used
   *
   * @return {@link ArrayList}&lt;{@link Location}&gt;
   */
  public ArrayList<Location> getOccupiedLocations() {
    ArrayList<Location> theLocations = new ArrayList<Location>();
    for (int r = 0; r < getNumRows(); r++) {
      for (int c = 0; c < getNumCols(); c++) {
        Location loc = new Location(r, c);
        if (get(loc) != null) {
          theLocations.add(loc);
        }
      }
    }

    return theLocations;
  }

  /**
   * Returns the location of the object
   *
   * @param obj The object
   * @return {@link Location}
   */
  public Location get(E obj) {
    for (int r = 0; r < getNumRows(); r++) {
      for (int c = 0; c < getNumCols(); c++) {
        Location loc = new Location(r, c);
        E objAtLoc = get(loc);
        if (objAtLoc != null && objAtLoc == obj) {
          return loc;
        }
      }
    }
    return null;
  }

  /**
   * Returns the object at the location specified
   *
   * @param loc The location
   * @return <E>
   */
  @SuppressWarnings("unchecked")
  public E get(Location loc) {
    if (!isValid(loc)) {
      throw new IllegalArgumentException("Location " + loc + " is not valid");
    }
    return (E) occupantArray[loc.getRow()][loc.getCol()];

  }

  /**
   * Puts the object at the location specified
   *
   * @param loc The location
   * @param obj The object
   * @return <E>
   */
  public E put(Location loc, E obj) {
    if (!isValid(loc)) {
      throw new IllegalArgumentException("Location " + loc + " is not valid");
    }
    if (obj == null) {
      throw new NullPointerException("obj == null");
    }

    // Add the object to the grid.
    E oldOccupant = get(loc);
    occupantArray[loc.getRow()][loc.getCol()] = obj;
    return oldOccupant;
  }

  /**
   * Builds a string representing the occupied locations Primarily used for debug purposes
   */
  @Override
  public String toString() {
    String s = "{";
    for (Location loc : getOccupiedLocations()) {
      if (s.length() > 1) {
        s += ", ";
      }
      s += loc + "=" + get(loc);
    }
    return s + "}";
  }

  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public E next() {
    return null;
  }

  @Override
  public void remove() {}
}
