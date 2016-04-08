package com.rom.quizup.client.ui.game;

import java.util.List;

import com.rom.quizup.client.helpers.BoundedGrid;
import com.rom.quizup.client.helpers.Location;
import com.rom.quizup.client.models.QuGame;
import com.rom.quizup.client.models.QuQuestion;
import com.rom.quizup.client.ui.game.models.BoardRenderModel;
import com.rom.quizup.client.ui.game.models.GridLayoutRenderModel;
import com.rom.quizup.client.ui.game.models.LetterRenderModel;
import com.rom.quizup.client.ui.game.models.QuestionRenderModel;

/**
 * Represents an adapter to be used to transform a QuGame into Render models for the GameActivity
 */
public class GameModelAdapter {

  /**
   * Project the {@link QuGame} data into a {@link BoardRenderModel}
   *
   * @param quGame The game model
   * @return {@link BoardRenderModel}
   */
  public static BoardRenderModel adapt(QuGame quGame) {
    BoardRenderModel result = new BoardRenderModel();

    result.setTotalTime(quGame.getBoard().getAllottedTime());

    for (QuQuestion question : quGame.getBoard().getQuestions()) {
      QuestionRenderModel wrm = new QuestionRenderModel();
      wrm.setQuestion(question.getQuestion());
      wrm.setWord(question.getWord().toString());
      result.getWords().add(wrm);
    }

    GridLayoutRenderModel layoutModel = new GridLayoutRenderModel();
    result.setGridLayout(layoutModel);

    List<List<Character>> boardDefinition = quGame.getBoard().getBoardDefinition();
    BoundedGrid<LetterRenderModel> boardRenderModel =
        new BoundedGrid<LetterRenderModel>(boardDefinition.size(), boardDefinition.size());
    layoutModel.setBoard(boardRenderModel);

    for (int r = 0; r <= boardDefinition.size() - 1; r++) {
      for (int c = 0; c <= boardDefinition.size() - 1; c++) {
        Location loc = new Location(r, c);
        LetterRenderModel letterRenderModel = new LetterRenderModel();
        letterRenderModel.setGlyph(boardDefinition.get(r).get(c));
        boardRenderModel.put(loc, letterRenderModel);
      }
    }

    return result;
  }
}
