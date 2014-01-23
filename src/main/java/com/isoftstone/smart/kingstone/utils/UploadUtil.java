package com.isoftstone.smart.kingstone.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.isoftstone.smart.core.util.FileUploadProgressListener;

public class UploadUtil implements Runnable {
  private Map<String, String> params;
  private Map<String, FileItem> files;

  private static final String[] pictype = {"BMP", "JPG", "JPEG", "PNG", "GIF"};

  public UploadUtil() {
    params = new HashMap<String, String>();
    files = new HashMap<String, FileItem>();
  }

  // 用来获取文件的名字
  public String getFileName(FileItem item) {
    String fName = item.getName();
    System.out.println("fname=====>" + fName);
    int lastIndex = fName.lastIndexOf("\\");
    fName = fName.substring(lastIndex + 1);
    System.out.println("new fname=====>" + fName);
    return fName;
  }

  public Map<String, FileItem> getFiles() {

    return files;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public boolean isPdf(String filename) {
    if (filename.indexOf(".") > 0) {
      String suffix = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

      if ("PDF".equals(suffix)) {
        return true;
      }
    }
    return false;
  }

  public boolean isPicRight(String filename) {
    if (filename.indexOf(".") > 0) {
      String suffix = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
      for (int i = 0; i < pictype.length; i++) {
        if (pictype[i].equals(suffix)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

  public void setMap(HttpServletRequest request) {
    // Create a factory for disk-based file items
    FileItemFactory factory = new DiskFileItemFactory();
    // Create a new file upload handler
    ServletFileUpload upload = new ServletFileUpload(factory);
    upload.setHeaderEncoding("utf-8");
    upload.setProgressListener(new FileUploadProgressListener());// 设置进度监听器
    // Parse the request
    try {
      @SuppressWarnings("unchecked")
      List<FileItem> items = upload.parseRequest(request);
      Iterator<FileItem> iter = items.iterator();
      while (iter.hasNext()) {
        FileItem item = (FileItem) iter.next();
        if (item.isFormField()) {
          String name = item.getFieldName();
          String value = item.getString();
          params.put(name, value);
        } else {
          String name = item.getFieldName();
          files.put(name, item);
        }
      }
    } catch (FileUploadException e) {
      e.printStackTrace();
    }
  }
}