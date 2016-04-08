package com.rom.quizup.client.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * The purpose of this class is to extend a normal {@link ImageView} to be round
 */
public class RoundImageView extends ImageView {

  /**
   * Constructor
   *
   * @param context The context
   */
  public RoundImageView(Context context) {
    super(context);
  }

  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   */
  public RoundImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * Constructor
   *
   * @param context The context
   * @param attrs The attributes
   * @param defStyle The style
   */
  public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    BitmapDrawable drawable = (BitmapDrawable) getDrawable();

    if (drawable == null) {
      return;
    }

    if (getWidth() == 0 || getHeight() == 0) {
      return;
    }

    Bitmap fullSizeBitmap = drawable.getBitmap();

    int scaledWidth = getMeasuredWidth();
    int scaledHeight = getMeasuredHeight();

    Bitmap mScaledBitmap;
    if (scaledWidth == fullSizeBitmap.getWidth() && scaledHeight == fullSizeBitmap.getHeight()) {
      mScaledBitmap = fullSizeBitmap;
    } else {
      mScaledBitmap = Bitmap.createScaledBitmap(fullSizeBitmap, scaledWidth, scaledHeight,
          /* filter */ true);
    }

    Bitmap roundBitmap = getRoundedCornerBitmap(getContext(),
        mScaledBitmap,
        this.getWidth() / 2,
        scaledWidth,
        scaledHeight,
        false,
        false,
        false,
        false);
    canvas.drawBitmap(roundBitmap, 0, 0, null);

  }

  /**
   * Builds the bitmap with rounded corners based upon the parameters
   *
   * @param context The context
   * @param input The bitmap
   * @param pixels The pixels
   * @param w The width
   * @param h The height
   * @param squareTL Specify if the top left should be square
   * @param squareTR Specify if the top right should be square
   * @param squareBL Specify if the bottom left should be square
   * @param squareBR Specify if the bottom right should be square
   * @return {@link Bitmap}
   */
  public Bitmap getRoundedCornerBitmap(Context context,
      Bitmap input,
      int pixels,
      int w,
      int h,
      boolean squareTL,
      boolean squareTR,
      boolean squareBL,
      boolean squareBR) {

    Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
    Canvas canvas = new Canvas(output);
    final float densityMultiplier = context.getResources().getDisplayMetrics().density;

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, w, h);
    final RectF rectF = new RectF(rect);

    // make sure that our rounded corner is scaled appropriately
    final float roundPx = pixels * densityMultiplier;

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

    // draw rectangles over the corners we want to be square
    if (squareTL) {
      canvas.drawRect(0, 0, w / 2, h / 2, paint);
    }
    if (squareTR) {
      canvas.drawRect(w / 2, 0, w, h / 2, paint);
    }
    if (squareBL) {
      canvas.drawRect(0, h / 2, w / 2, h, paint);
    }
    if (squareBR) {
      canvas.drawRect(w / 2, h / 2, w, h, paint);
    }

    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(input, 0, 0, paint);

    return output;
  }

}
