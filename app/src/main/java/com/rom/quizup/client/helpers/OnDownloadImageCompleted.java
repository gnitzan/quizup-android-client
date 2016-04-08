package com.rom.quizup.client.helpers;

import android.graphics.Bitmap;

/**
 * Interface that is used as a listener with the {@link DownloadImageTask}
 */
public interface OnDownloadImageCompleted {

  /**
   * Provides the listener to know when the image is downloaded
   *
   * @param image The bitmap that was downloaded
   */
  void onDownloadImage(Bitmap image);
}
