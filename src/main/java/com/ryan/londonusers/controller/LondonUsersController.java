package com.ryan.londonusers.controller;

import com.ryan.londonusers.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller defining the REST endpoint of the application.
 */
@RestController
public class LondonUsersController {

  @GetMapping(value = "/v1/london-users", produces = {"application/json"})
  public ResponseEntity<List<User>> getLondonUsers() {

    List<User> users = new ArrayList<>();

    return ResponseEntity.ok().body(users);
  }
}