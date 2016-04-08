package com.rom.quizup.client.providers;

import com.rom.quizup.client.ApplicationSettings;
import com.rom.quizup.client.models.Board;
import com.rom.quizup.client.models.Game;
import com.rom.quizup.client.models.GamePlay;
import com.rom.quizup.client.models.QuBoard;
import com.rom.quizup.client.models.QuGame;
import com.rom.quizup.client.models.QuGamePlay;
import com.rom.quizup.client.models.QuLetter;
import com.rom.quizup.client.models.QuQuestion;
import com.rom.quizup.client.models.QuWord;

import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of this class is to build a {@link QuGame} from cloud endpoint query results of
 * type {@link Game}
 *
 */
public class GameBuilder {

  public static QuGame buildGameModel(ApplicationSettings settings, Game result) {
    QuGame quGame = null;

    Board board = result.getBoard();

    // ensure that there are the same number of clues as answers
    if (board.getQuestions() != null && board.getAnswers() != null
        && board.getQuestions().size() == board.getAnswers().size()) {

      List<QuQuestion> questions = new ArrayList<QuQuestion>();

      for (int index = 0; index < board.getQuestions().size(); index++) {

        String question = board.getQuestions().get(index);
        String word = board.getAnswers().get(index);

        List<QuLetter> letters = new ArrayList<QuLetter>();
        QuWord quWord = new QuWord(letters);

        for (Character letter : word.toCharArray()) {
          letters.add(new QuLetter(letter));
        }

        questions.add(new QuQuestion(question, quWord));
      }

      List<List<Character>> boardDefinition = new ArrayList<List<Character>>();

      if (board.getGridDefinitions() != null && board.getGridDefinitions().size() > 0) {

        for (String row : board.getGridDefinitions()) {

          List<Character> characters = new ArrayList<Character>();

          for (Character letter : row.toCharArray()) {
            characters.add(letter);
          }

          boardDefinition.add(characters);
        }
      }

      GamePlay me = getGamePlayer(settings.getPlayerId(), result.getGamePlays());
      GamePlay otherPlayer = getOtherPlayer(settings.getPlayerId(), result.getGamePlays());

      QuGamePlay currentPlayer = new QuGamePlay(me.getPlayer().getId(),
              me.getPlayer().getNickname(), me.getTimeLeft(),
          me.getCorrectAnswers(), me.getIsWinner());
      QuGamePlay opponent = null;

      if (otherPlayer != null) {
        opponent = new QuGamePlay(
            otherPlayer.getPlayer().getId(), otherPlayer.getPlayer().getNickname(),
            otherPlayer.getTimeLeft(), otherPlayer.getCorrectAnswers(), otherPlayer.getIsWinner());
      }

      Boolean gameFinished = getFinishedGame(result.getGamePlays());

      quGame = new QuGame(
          result.getId(), new QuBoard(boardDefinition, questions, (long)board.getAllottedTime()),
          currentPlayer, opponent, gameFinished);
    }


    return quGame;
  }

  private static GamePlay getGamePlayer(String playerId, List<GamePlay> players) {

    for (GamePlay detail : players) {

      if (detail.getPlayer().getId().compareTo(playerId) == 0) {
        return detail;
      }
    }

    return null;
  }

  private static GamePlay getOtherPlayer(String playerId, List<GamePlay> players) {

    for (GamePlay detail : players) {
      if (detail.getPlayer().getId().compareTo(playerId) != 0) {
        return detail;
      }
    }

    return null;
  }

  private static boolean getFinishedGame(List<GamePlay> players) {

    for (GamePlay detail : players) {
      if (!detail.getFinished()) {
        return false;
      }
    }

    return true;

  }
}
