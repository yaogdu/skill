package com.isoftstone.smart.core.service;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.isoftstone.smart.core.WebPlatform;

public class Application {

  protected static final URI BASE_URI = URI.create("http://localhost:8080/smart/");

  public static void main(String[] args) {
    try {
      System.out.println("JSON with JAXB Jersey Example App");
      final Injector injector = Guice.createInjector(WebPlatform.getDefault());
      PersistService persistService = injector.getInstance(PersistService.class);
      persistService.start();
      WebResourceConfig app = injector.getInstance(WebResourceConfig.class);
      final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, app);
      System.out.println(String.format("Application started.%nTry out %s%nHit enter to stop it...", BASE_URI));
      System.in.read();
      server.stop();
      persistService.stop();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
