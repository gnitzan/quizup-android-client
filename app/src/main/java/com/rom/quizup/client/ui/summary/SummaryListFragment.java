package com.rom.quizup.client.ui.summary;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rom.quizup.client.R;
import com.rom.quizup.client.models.QuGame;
import com.rom.quizup.client.models.QuQuestion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this fragment is to manage the overall layout of the activity such as the header
 * and the list of answers
 */
public class SummaryListFragment extends ListFragment {

  private ListHeaderView listViewHeader;
  private GameSummaryItemAdapter listViewAdapter;
  private QuGame quGame;
  private OnSummaryListFragmentInteractionListener mListener;
  private OnClickListener homeOnClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      SummaryListFragment.this.getActivity().finish();
    }
  };

  /**
   * Constructor
   */
  public SummaryListFragment() {}

  /**
   * Hydrate the fragment with the game model
   *
   * @param model An instance of {@link QuGame}
   */
  public void hydrateState(QuGame model) {
    quGame = model;
    this.buildLayout();
  }

  /**
   * Inflate the summary list
   */
  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_summary_list, container, false);

    return view;
  }

  private void buildLayout() {

    ListView listView = (ListView) getActivity().findViewById(android.R.id.list);

    listViewHeader = new ListHeaderView(SummaryListFragment.this.getActivity(), null);

    listView.addHeaderView(listViewHeader);

    Button homeButton = (Button) listViewHeader.findViewById(R.id.homeButton);
    homeButton.setOnClickListener(homeOnClickListener);

    TextView gameResult = (TextView) listViewHeader.findViewById(R.id.gameResultText);
    TextView gameOverText = (TextView) listViewHeader.findViewById(R.id.gameOverText);

    String playerName = getString(R.string.summary_header_you);
    int playerCorrectGuesses = quGame.getCurrentPlayer().getAnswerIndexes().size();

    List<QuQuestion> questions = quGame.getBoard().getQuestions();

    Map<Integer, WordGuessResult> listItems = new HashMap<>();

    int questionId = 0;

    for (QuQuestion question : questions) {
      listItems.put(questionId++, new WordGuessResult(question.getWord().toString(), false, false));
    }

    List<Integer> playerCorrectIndexes = quGame.getCurrentPlayer().getAnswerIndexes();

    for (Integer index : playerCorrectIndexes) {
      try {

        WordGuessResult wgr = listItems.get(index);

        wgr.player1GuessedCorrect = true;
      }
      catch(Exception e) {
        e.printStackTrace();
      }
    }

    if (playerCorrectIndexes.size() == questions.size()) {
      gameOverText.setText(getString(R.string.summary_perfect));
    }

    int adapterLayout = R.layout.list_item_game_summary_single;

    gameResult.setText(getString(
        R.string.num_questions_correct, Integer.toString(playerCorrectIndexes.size()),
        Integer.toString(questions.size())));

    if (quGame.getOpponent() != null) {

      List<Integer> opponentCorrectIndexes = quGame.getOpponent().getAnswerIndexes();

      String opponentName = quGame.getOpponent().getNickName();
      int opponentCorrectGuesses = quGame.getOpponent().getAnswerIndexes().size();

      for (Integer index : opponentCorrectIndexes) {
        try {

          WordGuessResult wgr = listItems.get(index);

          wgr.player2GuessedCorrect = true;
        }
        catch(Exception e) {
          e.printStackTrace();
        }
      }

      if (quGame.getCurrentPlayer().getIsWinner()) {

        if (playerCorrectGuesses == opponentCorrectGuesses) {
          gameResult.setText(getString(R.string.summary_you_finished_before, opponentName));
          gameOverText.setText(getString(R.string.you_won));
        } else {
          gameResult.setText(getString(R.string.someone_beat_someone, playerName, opponentName,
              Integer.toString(playerCorrectGuesses), Integer.toString(opponentCorrectGuesses)));
        }
      } else if (quGame.getOpponent().getIsWinner()) {

        if (playerCorrectGuesses == opponentCorrectGuesses) {
          gameResult.setText(getString(R.string.summary_finished_before_you, opponentName));
          gameOverText.setText(getString(R.string.you_lost));
        } else {
          gameResult.setText(getString(R.string.someone_beat_someone, opponentName, playerName,
              Integer.toString(opponentCorrectGuesses), Integer.toString(playerCorrectGuesses)));
        }
      } else {
        // tie
        gameResult.setText(getString(R.string.tie));
      }

      listViewHeader.setLeftHeaderText(playerName);
      listViewHeader.setRightHeaderText(opponentName);
      adapterLayout = R.layout.list_item_game_summary_double;
    }

    WordGuessResult[] itemArray = listItems.values().toArray(new WordGuessResult[] {});
    listViewAdapter = new GameSummaryItemAdapter(
        SummaryListFragment.this.getActivity(), adapterLayout, itemArray);

    setListAdapter(listViewAdapter);
  }

  /**
   * Assign the listener to the activity
   */
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (OnSummaryListFragmentInteractionListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(
          activity.toString() + " must implement OnSummaryListFragmentInteractionListener");
    }
  }

  /**
   * Assign null to the listener
   */
  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * Notify the listener of fragment interaction
   */
  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);

    if (null != mListener) {
      // Notify the active callbacks
      mListener.onFragmentInteraction();
    }
  }


  /**
   * Interface used to inform the activity of any fragment interaction
   */
  public interface OnSummaryListFragmentInteractionListener {
    public void onFragmentInteraction();
  }
}
