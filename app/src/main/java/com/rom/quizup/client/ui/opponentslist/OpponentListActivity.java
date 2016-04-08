package com.rom.quizup.client.ui.opponentslist;

import com.rom.quizup.client.R;
import com.rom.quizup.client.models.InvitationStatus;
import com.rom.quizup.client.models.QuInvitation;
import com.rom.quizup.client.models.Player;
import com.rom.quizup.client.providers.OnCancelInvitationCompleted;
import com.rom.quizup.client.providers.OnGetOpponentResponseToInvitationCompleted;
import com.rom.quizup.client.providers.OnGetPlayerListCompleted;
import com.rom.quizup.client.providers.OnSendInvitationCompleted;
import com.rom.quizup.client.ui.ApplicationDialogs;
import com.rom.quizup.client.ui.BaseActivity;
import com.rom.quizup.client.ui.IntentExtras;
import com.rom.quizup.client.ui.RequestCodes;
import com.rom.quizup.client.ui.WaitOverlayFragment;
import com.rom.quizup.client.ui.lobby.LobbyActivity;
import com.rom.quizup.client.ui.opponentslist.OpponentListFragment.OpponentListFragmentInteractionListener;
import com.rom.quizup.client.ui.opponentslist.models.OpponentItem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The purpose of this activity is to support the display of a list of either Google+ opponents or a
 * general list of opponents registered in the App Engine as well as the ability to send an
 * invitation to an opponent
 */
public class OpponentListActivity extends BaseActivity
    implements
    OpponentListFragmentInteractionListener,
    OnGetPlayerListCompleted,
    OnSendInvitationCompleted,
    OnClickListener,
    OnCancelInvitationCompleted {

  private static final String TAG = OpponentListActivity.class.getSimpleName();

  private WaitOverlayFragment waitFragment;

  private Timer timer = new Timer();

  private AlertDialog inviteDialog;

  private String currentInvitationId;

  private String currentGameId;

  private OpponentItem selectedOpponentItem;

  private boolean attemptingToCancel = false;

  private TabButton yourCirclesButton;

  private TabButton allOpponentsButton;

  private Map<String, OpponentItem> opponents = new LinkedHashMap<String, OpponentItem>();

  /**
   * Display the "spinny" fragment and begin the loading of either Google+ opponents or general game
   * opponents
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    currentInvitationId = null;
    currentGameId = null;

    setContentView(R.layout.activity_opponent_list);

    waitFragment = new WaitOverlayFragment();

    getFragmentManager().beginTransaction().add(android.R.id.content, waitFragment).commit();

    getFragmentManager().beginTransaction().show(waitFragment).commit();

    loadPlayerList();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return false;
  }


  /**
   * Occurs when an opponent is selected in the list. Sends an invitation to the opponent
   */
  @Override
  public void onFragmentInteraction(String id) {

    if (!attemptingToCancel) {
      selectedOpponentItem = opponents.get(id);

      showInviteDialog();

      if (selectedOpponentItem.isPlusId) {
        mApplication.getDataProvider().sendInvitation(selectedOpponentItem.id, this);
      } else {
        mApplication.getDataProvider().sendInvitation(selectedOpponentItem.id, this);
      }

    } else {
      ApplicationDialogs.displayAlert(this, getString(R.string.attemptingToCancelinvitation));
    }

  }

  /**
   * Loads the opponents into the list fragment
   */
  @Override
  public void onGetPlayerListCompleted(List<Player> result) {

    opponents.clear();

    if (result != null) {

      for (Player model : result) {
        opponents.put(model.getId().toString(),
            new OpponentItem(model.getId().toString(), model.getNickname()));
      }
    }

    OpponentListFragment fragment =
        (OpponentListFragment) getFragmentManager().findFragmentById(R.id.opponent_list_fragment);


    if (fragment != null && fragment.getView() != null) {
      fragment.setList(opponents);
      getFragmentManager().beginTransaction().hide(waitFragment).commit();
    }

    if (!opponents.isEmpty()) {
      fragment.setShowNoOpponents(false);
    } else {
      fragment.setShowNoOpponents(true);
    }

  }

  /**
   * Handles the result of sending an invitation
   */
  @Override
  public void onSendInvitationCompleted(QuInvitation result) {

    if (result != null) {

      if (result.getStatus().equals(InvitationStatus.DECLINED)) {
        currentInvitationId = null;
        currentGameId = null;
        inviteDialog.dismiss();
        ApplicationDialogs.displayAlert(this, getString(R.string.playerNotRegisteredInQuizUp));
      } else {

        currentInvitationId = result.getInvitationId();
        currentGameId = result.getGameId();

        if (attemptingToCancel) {
          mApplication.getDataProvider().cancelInvitation(currentGameId, currentInvitationId, this);
        } else {
          timer.schedule(new CheckOpponentInvitationResponse(), 0);
        }
      }

    } else {
      currentInvitationId = null;
      currentGameId = null;
      inviteDialog.dismiss();
      ApplicationDialogs.displayAlert(this, getString(R.string.unableToSendInvitation));
    }
  }

  /**
   * The purpose of this class is to check the status of a sent invitation
   */
  private class CheckOpponentInvitationResponse extends TimerTask implements OnGetOpponentResponseToInvitationCompleted {

    @Override
    public void run() {
      if (currentGameId != null && currentInvitationId != null) {
        mApplication.getDataProvider().getOpponentResponseToInvitation(currentGameId, currentInvitationId, this);
      }
    }

    @Override
    public void onGetOpponentResponseToInvitationCompleted(QuInvitation invitation) {

      if (currentInvitationId == null || currentGameId == null || attemptingToCancel) {
        return;
      }

      if (invitation != null && invitation.getStatus().equals(InvitationStatus.SENT)) {
        // run again in 1 second
        timer.schedule(new CheckOpponentInvitationResponse(), 1000);
      } else {

        if (inviteDialog != null) {
          inviteDialog.dismiss();
        }

        if (invitation != null && invitation.getStatus().equals(InvitationStatus.ACCEPTED)) {

          // send off to lobby
          Intent intent = new Intent(OpponentListActivity.this, LobbyActivity.class);
          intent.putExtra(IntentExtras.LOBBY_GAME_TYPE, IntentExtras.LOBBY_MULTI_PLAYER);
          intent.putExtra(IntentExtras.LOBBY_GAME_ID, currentGameId);
          startActivityForResult(intent, RequestCodes.REQUEST_GAME_RESULT);

        } else {
          ApplicationDialogs.displayAlert(OpponentListActivity.this, selectedOpponentItem.content,
                  OpponentListActivity.this.getString(R.string.challengeDeclined));
        }
      }
    }
  }

  private void showInviteDialog() {

    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setNegativeButton(R.string.cancelChallenge, this);

    builder.setMessage(R.string.sendingChallenge);
    builder.setTitle(selectedOpponentItem.content);
    builder.setCancelable(false);
    inviteDialog = builder.create();

    inviteDialog.show();

    styleDialogButtons(inviteDialog);
  }

  /**
   * Handles the click events from the send invitation dialog
   */
  @Override
  public void onClick(DialogInterface dialog, int which) {
    switch (which) {
      case DialogInterface.BUTTON_NEGATIVE:
        attemptingToCancel = true;

        // can't cancel because there isn't a current invitation
        if (currentGameId != null && currentInvitationId != null) {
          mApplication.getDataProvider().cancelInvitation(currentGameId, currentInvitationId, this);
        }
        break;
    }
  }

  /**
   * Handles the result of a canceled invitation
   */
  @Override
  public void onCancelInvitationCompleted(Boolean result) {

    if (!result) {
      attemptingToCancel = false;
      if (inviteDialog.getContext() != null) {
        inviteDialog.setMessage(this.getString(R.string.opponentAcceptedCantCancel));
        inviteDialog.show();
      }
    } else {
      // cancel the timer so polling stops
      currentInvitationId = null;
      currentGameId = null;
      attemptingToCancel = false;
    }
  }

  /**
   * Set the result and finish the activity if the request code is a finished game
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    switch (requestCode) {
      case RequestCodes.REQUEST_GAME_RESULT:
        setResult(resultCode, data);
        finish();
        break;
    }
  }

  private void loadPlayerList() {
    mApplication.getDataProvider().getPlayerList(this);
  }
}
