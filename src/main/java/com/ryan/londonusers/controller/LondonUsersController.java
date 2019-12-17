package com.ryan.londonusers.controller;

import com.ryan.londonusers.model.User;
import com.ryan.londonusers.service.UsersInOrAroundLondonService;
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

  /**
   * UsersInOrAroundCityService init.
   */
  private final UsersInOrAroundLondonService usersInOrAroundLondonService;

  /**
   * Constructor for controller autowiring service.
   *
   * @param usersInOrAroundLondonService - service to get users in our around city.
   */
  @Autowired
  public LondonUsersController(UsersInOrAroundLondonService usersInOrAroundLondonService) {
    this.usersInOrAroundLondonService = usersInOrAroundLondonService;
  }

  @GetMapping(value = "/v1/london-users", produces = {"application/json"})
  public ResponseEntity<List<User>> getLondonUsers() {

    List<User> londonUsers = usersInOrAroundLondonService.getUsersInOrAroundLondonService();

    return ResponseEntity.ok().body(londonUsers);
  }
}