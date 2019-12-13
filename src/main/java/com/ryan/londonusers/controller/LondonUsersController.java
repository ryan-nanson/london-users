package com.ryan.londonusers.controller;

import com.ryan.londonusers.model.User;
import com.ryan.londonusers.service.UsersFromCityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller defining the REST endpoint of the application.
 */
@RestController
public class LondonUsersController {

  private final UsersFromCityService usersFromCityService;

  @Autowired
  public LondonUsersController(UsersFromCityService usersFromCityService) {
    this.usersFromCityService = usersFromCityService;
  }

  @GetMapping(value = "/v1/london-users", produces = {"application/json"})
  public ResponseEntity<List<User>> getLondonUsers() {

    List<User> users = usersFromCityService.getUsersFromCity("London");

    return ResponseEntity.ok().body(users);
  }
}