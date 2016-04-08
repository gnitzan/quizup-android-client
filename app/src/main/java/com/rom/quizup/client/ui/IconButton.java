package com.rom.quizup.client.ui;

import com.rom.quizup.client.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Icon button.
 */
public class IconButton extends RelativeLayout {

  private TextView textView;
  private ImageView imageView;
  private Drawable imageSource;

  public IconButton(Context context, AttributeSet attrs) {
    this(context, attrs, 0);

  }

  public IconButton(Context context) {
    this(context, null);
  }

  public IconButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    layoutInflater.inflate(R.layout.icon_button, this, true);
    this.textView = (TextView) findViewById(R.id.textView);
    this.imageView = (ImageView) findViewById(R.id.imageView);


    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IconButton);
    String s = a.getString(R.styleable.IconButton_buttonText);
    Drawable i = a.getDrawable(R.styleable.IconButton_imageDrawable);
    if (s != null) {
      this.setButtonText(s);
    }
    if (i != null) {
      this.setImageDrawable(i);
    }

    a.recycle();
  }

  public void setButtonText(String buttonText) {
    this.textView.setText(buttonText);
  }

  public String getButtonText() {
    return (String) this.textView.getText();
  }

  public Drawable getImageDrawable() {
    return imageSource;
  }

  public void setImageDrawable(Drawable imageSource) {
    this.imageSource = imageSource;
    this.imageView.setImageDrawable(imageSource);
  }

}
