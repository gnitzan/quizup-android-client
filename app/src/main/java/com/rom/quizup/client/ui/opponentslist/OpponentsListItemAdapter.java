package com.rom.quizup.client.ui.opponentslist;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rom.quizup.client.R;
import com.rom.quizup.client.helpers.DownloadImageTask;
import com.rom.quizup.client.ui.opponentslist.models.OpponentItem;

/**
 * Adapter used for managing the list of opponents
 */
public class OpponentsListItemAdapter extends ArrayAdapter<OpponentItem> {
  private final Context context;
  private final OpponentItem[] values;
  private int layoutResourceId;

  /**
   * Constructor
   *
   * @param context The context
   * @param layoutResourceId The layout resource ID
   * @param values The list of opponents
   */
  public OpponentsListItemAdapter(Context context, int layoutResourceId, OpponentItem[] values) {
    super(context, layoutResourceId, values);
    this.layoutResourceId = layoutResourceId;
    this.context = context;
    this.values = values;
  }

  /**
   * Builds the opponent item for the view
   */
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    View rowView = inflater.inflate(layoutResourceId, parent, false);

    TextView textView = (TextView) rowView.findViewById(R.id.opponentItemText);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.opponentIcon);

    OpponentItem item = values[position];

    textView.setText(item.content);

    if (item.imageUrl != null) {
      new DownloadImageTask(imageView).executeOnExecutor(
          AsyncTask.THREAD_POOL_EXECUTOR, item.imageUrl);
    } else {
      imageView.setImageResource(R.drawable.avatar_small);
    }

    return rowView;
  }


}
