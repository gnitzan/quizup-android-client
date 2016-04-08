package com.rom.quizup.client.ui.game.models;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Represents the simulation model for the game board. This model en-cases state logic for user,
 * game and view state. This model is persistable and mutable
 */
public class BoardRenderModel {
  private int width;
  private int height;

  private String remainingTime;
  private long remainingTimeMilliseconds;

  private ArrayList<QuestionRenderModel> words = new ArrayList<QuestionRenderModel>();
  private int currentQuestionIndex = 0;
  private GridLayoutRenderModel gridLayout;
  private long totalTime;

  /**
   * Returns <code>true</code> if the number of selected letters is greater than the length of the
   * longest word
   *
   * @return {@link Boolean}
   */
  public Boolean isSelectionCountGreaterThanMaxWordLength() {
    if (gridLayout.getSelectedLetters().length > getMaxWordLength()) {
      return true;
    }
    return false;
  }

  /**
   * Returns <code>true</code> if the current selection correctly spells the current answer
   *
   * @return {@link Boolean}
   */
  public Boolean isCurrentQuestionAnsweredBySelection() {
    QuestionRenderModel currentQuestion = words.get(currentQuestionIndex);
    if (currentQuestion.getWord().toLowerCase().equals(gridLayout.getSelectedWord().toLowerCase())) {
      return true;
    }
    return false;
  }

  /**
   * Returns the max word length of all the answers
   *
   * @return The number of characters in the longest word
   */
  public int getMaxWordLength() {
    int maxLengthOfWord = 0;
    for (QuestionRenderModel word : words) {
      if (word.getWord().length() > maxLengthOfWord) {
        maxLengthOfWord = word.getWord().length();
      }
    }
    return maxLengthOfWord;
  }

  /**
   * Gets the width
   *
   * @return The width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns <code>true</code> if all the questions have been answered
   *
   * @return {@link Boolean}
   */
  public Boolean getAllQuestionsCompleted() {
    for (QuestionRenderModel wordRenderModel : words) {
      if (!wordRenderModel.getCompleted()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns <code>true</code> if there is more than one question left that hasn't been correctly
   * answered.
   *
   * @return {@link Boolean}
   */
  public Boolean getIsMoreThanOneQuestionLeft() {
    int count = 0;
    for (QuestionRenderModel wordRenderModel : words) {
      if (!wordRenderModel.getCompleted()) {
        count++;
      }
    }
    return count > 1;
  }

  /**
   * Returns the index of the next unanswered question
   *
   * @return The index
   */
  public int getIndexOfNextUnansweredQuestion() {
    if (!getAllQuestionsCompleted()) {
      int nextWordIndex = currentQuestionIndex;
      do {
        if (getAllQuestionsCompleted()) {
          return -1;
        }

        nextWordIndex++;
        if (nextWordIndex >= words.size()) {
          nextWordIndex = 0;
        }
      } while (words.get(nextWordIndex).getCompleted());
      return nextWordIndex;
    }
    return -1;
  }

  /**
   * Sets the width
   *
   * @param width The width
   */
  public void setWidth(int width) {
    this.width = width;
  }

  /**
   * Gets the height
   *
   * @return The height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Sets the height
   *
   * @param height The height
   */
  public void setHeight(int height) {
    this.height = height;
  }

  /**
   * Returns the list of words/answers
   *
   * @return {@link ArrayList}&lt;{@link QuestionRenderModel}&gt;
   */
  public ArrayList<QuestionRenderModel> getWords() {
    return words;
  }

  /**
   * Sets the words/answers
   *
   * @param words The answers to the questions
   */
  public void setWords(ArrayList<QuestionRenderModel> words) {
    this.words = words;
  }

  /**
   * Returns the current question's index
   *
   * @return The index
   */
  public Integer getCurrentQuestionIndex() {
    return currentQuestionIndex;
  }

  /**
   * Sets the current word index
   *
   * @param currentWordIndex The index
   */
  public void setCurrentWordIndex(Integer currentWordIndex) {
    this.currentQuestionIndex = currentWordIndex;
  }

  /**
   * Returns the {@link GridLayoutRenderModel}
   *
   * @return {@link GridLayoutRenderModel}
   */
  public GridLayoutRenderModel getGridLayout() {
    return gridLayout;
  }

  /**
   * Set the grid layout
   *
   * @param gridLayout The {@link GridLayoutRenderModel}
   */
  public void setGridLayout(GridLayoutRenderModel gridLayout) {
    this.gridLayout = gridLayout;
  }

  /**
   * Returns the number of questions
   *
   * @return The number of questions
   */
  public int getQuestionCount() {
    return words == null ? 0 : words.size();
  }

  /**
   * Return the remaining time
   *
   * @return {@link String}
   */
  public String getRemainingTime() {
    return remainingTime;
  }

  /**
   * Set the remaining time
   *
   * @param remainingTime The remaining time
   */
  public void setRemainingTime(String remainingTime) {
    this.remainingTime = remainingTime;
  }

  /**
   * Set the remaining time
   *
   * @param millisUntilFinished The remaining time
   */
  public void setRemainingTime(long millisUntilFinished) {
    this.remainingTimeMilliseconds = millisUntilFinished;
    this.remainingTime = String.format("%d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));
  }

  /**
   * Get the total time allotted to finish the game
   *
   * @return The time in milliseconds
   */
  public long getTotalTime() {
    return totalTime;
  }

  /**
   * Set the total time in milliseconds allotted for the game
   *
   * @param totalTime The total time in milliseconds
   */
  public void setTotalTime(long totalTime) {
    this.totalTime = totalTime;
  }

  /**
   * Get the remaining time in milliseconds
   *
   * @return The remaining time in milliseconds
   */
  public long getRemainingTimeMilliseconds() {
    return remainingTimeMilliseconds;
  }
}
