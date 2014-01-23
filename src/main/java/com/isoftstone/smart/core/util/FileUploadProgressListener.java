package com.isoftstone.smart.core.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.fileupload.ProgressListener;

public class FileUploadProgressListener implements ProgressListener {

  /** Logger */
  private static final Logger logger = Logger.getLogger(FileUploadProgressListener.class.getName());

  private long bytesRead;

  private long contentLength;

  private long startTime = -1;

  private long currTime = -1;

  public long getBytesRead() {
    return bytesRead;
  }

  public long getContentLength() {
    return contentLength;
  }

  public double getTransferRate() {
    double transferRate = 0.0;
    long timeInMillis = currTime - startTime;

    if (timeInMillis > 0) {
      transferRate = bytesRead * 1000 / timeInMillis;
    }

    return transferRate;
  }

  @Override
  public void update(final long bytesRead, final long contentLength, final int item) {
    final String method = "update";
    if (logger.isLoggable(Level.FINER)) {
      logger.entering(getClass().getName(), method, new Object[] {bytesRead, contentLength, item});
    }

    if (startTime < 0) {
      startTime = System.currentTimeMillis();
    }

    currTime = System.currentTimeMillis();

    this.bytesRead = bytesRead;
    this.contentLength = contentLength;

    if (logger.isLoggable(Level.FINER)) {
      logger.exiting(getClass().getName(), method);
    }
  }
}
