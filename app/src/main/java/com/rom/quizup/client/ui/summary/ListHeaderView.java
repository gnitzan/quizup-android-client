package com.rom.quizup.client.ui.summary;


import com.rom.quizup.client.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * The purpose of this view is to format the display of the header on the summary activity
 */
public class ListHeaderView extends RelativeLayout {

  private TextView leftHeader;
  private TextView rightHeader;
  private String leftHeaderText;
  private String rightHeaderText;


  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   * @param defStyle
   */
  public ListHeaderView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   */
  public ListHeaderView(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    layoutInflater.inflate(R.layout.list_header_game_summary, this, true);

    leftHeader = (TextView) findViewById(R.id.resultsTextView1);
    rightHeader = (TextView) findViewById(R.id.resultsTextView2);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ListHeaderView);
    String l = a.getString(R.styleable.ListHeaderView_leftHeaderText);
    String r = a.getString(R.styleable.ListHeaderView_rightHeaderText);
    if (l != null) {
      this.setLeftHeaderText(l);
    }
    if (r != null) {
      this.setRightHeaderText(r);
    }

    a.recycle();
  }

  /**
   * Constructor
   *
   * @param context The context
   */
  public ListHeaderView(Context context) {
    super(context);
  }

  /**
   * Get the left header text
   *
   * @return {@link String}
   */
  public String getLeftHeaderText() {
    return leftHeaderText;
  }

  /**
   * Set the left header text
   *
   * @param leftHeaderText The text
   */
  public void setLeftHeaderText(String leftHeaderText) {
    this.leftHeaderText = leftHeaderText;
    leftHeader.setText(leftHeaderText);
  }

  /**
   * Get the right header text
   *
   * @return {@link String}
   */
  public String getRightHeaderText() {
    return rightHeaderText;
  }

  /**
   * Set the right header text
   *
   * @param rightHeaderText The text
   */
  public void setRightHeaderText(String rightHeaderText) {
    this.rightHeaderText = rightHeaderText;
    rightHeader.setText(rightHeaderText);
  }

}
