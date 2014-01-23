package com.isoftstone.smart.core.inject;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Set;

import org.hibernate.ejb.packaging.NativeScanner;

import com.isoftstone.smart.core.WebPlatform;

public class EntityScanner extends NativeScanner {

  @Override
  public Set<Class<?>> getClassesInJar(URL jartoScan, Set<Class<? extends Annotation>> annotationsToLookFor) {
    return WebPlatform.getDefault().getEntities();
    // Set<Class<?>> classes = super.getClassesInJar(jartoScan, annotationsToLookFor);
    // Iterator<Application> appItr = WebPlatform.getApps();
    // while (appItr.hasNext()) {
    // Class<?> cls = appItr.next().getClass();
    // String entry = cls.getName().replaceAll("\\.", "/") + ".class";
    // try {
    // Enumeration<URL> xmls = Thread.currentThread().getContextClassLoader().getResources(entry);
    // while (xmls.hasMoreElements()) {
    // URL url = xmls.nextElement();
    // URL jarUrl = JarVisitorFactory.getJarURLFromURLEntry(url, entry);
    // Set<Class<?>> dpClasses = super.getClassesInJar(jarUrl, annotationsToLookFor);
    // classes.addAll(dpClasses);
    // }
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // // visit(repoType, classes, visited, annotationsToLookFor);
    // // visit(Reference.class, classes, visited, annotationsToLookFor);
    // return classes;
  }

}
