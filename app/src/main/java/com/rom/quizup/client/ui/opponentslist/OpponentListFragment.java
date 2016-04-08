package com.rom.quizup.client.ui.opponentslist;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Map;

import com.rom.quizup.client.R;
import com.rom.quizup.client.ui.opponentslist.models.OpponentItem;

/**
 * The purpose of this fragment is to display a list of opponents
 */
public class OpponentListFragment extends ListFragment implements AbsListView.OnItemClickListener {

  private RelativeLayout noOpponentContainer;

  private Boolean showNoOpponents;

  private OpponentListFragmentInteractionListener mListener;

  private AbsListView mListView;

  private OpponentItem[] opponents;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon
   * screen orientation changes).
   */
  public OpponentListFragment() {}

  /**
   * Set the list adapter and initialize click event
   */
  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_opponentlist, container, false);

    // Set the adapter
    mListView = (AbsListView) view.findViewById(android.R.id.list);

    // Set OnItemClickListener so we can be notified on item clicks
    mListView.setOnItemClickListener(this);

    this.noOpponentContainer = (RelativeLayout) view.findViewById(R.id.outerContainer);

    return view;
  }

  /**
   * Assign the listener activity
   */
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mListener = (OpponentListFragmentInteractionListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(
          activity.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  /**
   * Set the listener to null
   */
  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /**
   * Call the listener's {@link OpponentListFragmentInteractionListener#onFragmentInteraction}
   */
  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    if (null != mListener) {
      // Notify the active callbacks interface (the activity, if the
      // fragment is attached to one) that an item has been selected.
      mListener.onFragmentInteraction(this.opponents[position].id);

    }
  }

  /**
   * The default content for this Fragment has a TextView that is shown when the list is empty. If
   * you would like to change the text, call this method to supply the text it should use.
   */
  @Override
  public void setEmptyText(CharSequence emptyText) {
    View emptyView = mListView.getEmptyView();

    if (emptyText instanceof TextView) {
      ((TextView) emptyView).setText(emptyText);
    }
  }

  /**
   * Define interface for listener to react to item selections from the list fragment
   */
  public interface OpponentListFragmentInteractionListener {
    public void onFragmentInteraction(String id);
  }

  /**
   * Refresh the list
   */
  public void setList(Map<String, OpponentItem> opponents) {

    this.opponents = opponents.values().toArray(new OpponentItem[opponents.size()]);
    OpponentsListItemAdapter adapter = new OpponentsListItemAdapter(
        OpponentListFragment.this.getActivity(), R.layout.list_item_opponents_avatar,
        this.opponents);

    setListAdapter(adapter);

    mListView.invalidateViews();

    mListView.setOnItemClickListener(this);
  }

  /**
   * Returns <code>true</code> if it is showing the no opponents view
   *
   * @return {@link Boolean}
   */
  public Boolean getShowNoOpponents() {
    return showNoOpponents;
  }

  /**
   * Sets the value indicating whether or not the no opponents view should display
   *
   * @param showNoOpponents {@link Boolean} value indicating the visibility
   */
  public void setShowNoOpponents(Boolean showNoOpponents) {
    this.showNoOpponents = showNoOpponents;
    if (showNoOpponents) {
      this.noOpponentContainer.setVisibility(View.VISIBLE);
    } else {
      this.noOpponentContainer.setVisibility(View.GONE);
    }
  }

}
