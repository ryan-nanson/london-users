package com.ryan.londonusers.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.ryan.londonusers.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class LondonUsersControllerTest {

  @Test
  public void getLondonUsers() {
    LondonUsersController londonUsersController = new LondonUsersController();
    ResponseEntity<List<User>> responseEntity = londonUsersController.getLondonUsers();

    assertThat("Controller returns empty list", responseEntity.getBody(), is(new ArrayList()));
  }
}