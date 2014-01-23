package com.isoftstone.smart.kingstone.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

  private static int EXECUTOR_SIZE = 10;

  private static ThreadPool tp;

  public static synchronized ThreadPool instance() {
    if (tp == null) {
      tp = new ThreadPool();
    }
    return tp;
  }

  private ExecutorService service;

  public ThreadPool() {
    service = Executors.newFixedThreadPool(EXECUTOR_SIZE);
    System.out.println("Service:" + service);
  }

  public void submit(Runnable thread) {
    service.submit(thread);
  }

}
