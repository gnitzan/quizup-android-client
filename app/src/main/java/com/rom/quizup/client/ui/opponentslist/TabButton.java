package com.rom.quizup.client.ui.opponentslist;

import com.rom.quizup.client.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Represents a tab button
 */
public class TabButton extends RelativeLayout {

  private String tabText;
  private TextView tabTextView;


  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   * @param defStyle
   */
  public TabButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    layoutInflater.inflate(R.layout.tab_button, this, true);
    this.tabTextView = (TextView) findViewById(R.id.tabTextView);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabButton);
    String s = a.getString(R.styleable.TabButton_tabText);
    if (s != null) {
      this.setTabText(s);
    }

    a.recycle();
  }

  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   */
  public TabButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  /**
   * Constructor
   *
   * @param context The context
   */
  public TabButton(Context context) {
    this(context, null);
  }

  /**
   * Returns the tab's text
   *
   * @return {@link String}
   */
  public String getTabText() {
    return tabText;
  }

  /**
   * Sets the tab's text
   *
   * @param tabString The text to display
   */
  public void setTabText(String tabString) {
    this.tabText = tabString;
    this.tabTextView.setText(tabString);
  }
}
