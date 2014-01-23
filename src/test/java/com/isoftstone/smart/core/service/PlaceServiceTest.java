package com.isoftstone.smart.core.service;

import static org.junit.Assert.assertNotNull;

import javax.persistence.NoResultException;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.google.inject.Inject;
import com.isoftstone.smart.core.entity.Place;

public class PlaceServiceTest extends BaseTest {

  @Inject
  AccountService userService;

  @Inject
  PlaceService placeService;

  @Test
  public void testPlaceCRUD() throws JAXBException {
    Place root = placeService.getRootPlace();
    String path = "myplace";
    Place place;
    try {
      place = placeService.getByName(path);
    } catch (NoResultException e) {
      place = new Place();
      place.setPlace(root);
      place = placeService.save(place);
    }
    assertNotNull(place.getId());
  }
}
