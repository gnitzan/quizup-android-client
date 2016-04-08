package com.rom.quizup.client.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * The purpose of this class is to provide the functionality to download an image from the url
 * asynchronously
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

  private static final String LOG_TAG = DownloadImageTask.class.getSimpleName();

  private ImageView bmImage;

  private OnDownloadImageCompleted listener;

  /**
   * Constructor
   *
   * @param bmImage The {@link ImageView} to assign the image to
   * @param listener The listener who is called once the download has completed
   */
  public DownloadImageTask(ImageView bmImage, OnDownloadImageCompleted listener) {
    this.bmImage = bmImage;
    this.listener = listener;
  }

  /**
   * Constructor
   *
   * @param bmImage The {@link ImageView} to assign the image to
   */
  public DownloadImageTask(ImageView bmImage) {
    this.bmImage = bmImage;
  }

  @Override
  protected Bitmap doInBackground(String... urls) {
    String urldisplay = urls[0];
    Bitmap mIcon11 = null;

    InputStream in = null;
    try {
      in = new java.net.URL(urldisplay).openStream();
    } catch (MalformedURLException e) {
      Log.e(LOG_TAG, "doInBackground", e);
      e.printStackTrace();
    } catch (IOException e) {
      Log.e(LOG_TAG, "doInBackground", e);
      e.printStackTrace();
    }

    if (in != null) {
      mIcon11 = BitmapFactory.decodeStream(in);
    }

    return mIcon11;
  }

  @Override
  protected void onPostExecute(Bitmap result) {

    if (listener != null) {
      listener.onDownloadImage(result);
    }

    bmImage.setImageBitmap(result);
    bmImage.invalidate();
  }
}
