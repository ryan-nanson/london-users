package com.ryan.londonusers.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ryan.londonusers.model.User;
import com.ryan.londonusers.service.UsersFromCityService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

public class LondonUsersControllerTest {

  private UsersFromCityService usersFromCityService;
  private LondonUsersController londonUsersController;

  @Before
  public void setUp() {
    usersFromCityService = mock(UsersFromCityService.class);
    londonUsersController = new LondonUsersController(usersFromCityService);
  }

  @Test
  public void givenNoUsers_GetLondonUsers_ReturnsEmptyList() {

    when(usersFromCityService.getUsersFromCity("London")).thenReturn(new ArrayList<>());

    ResponseEntity<List<User>> responseEntity = londonUsersController.getLondonUsers();

    assertThat("Controller returns empty list", responseEntity.getBody(), is(new ArrayList()));
  }

  @Test
  public void givenOneMockedUser_GetLondonUsers_ReturnsOneUserInList() {

    User userJohn = new User(
        1,
        "John",
        "Smith",
        "email@domain.com",
        "0.0.0.0",
        123.456,
        654.321);

    List<User> users = new ArrayList<>();
    users.add(userJohn);

    when(usersFromCityService.getUsersFromCity("London")).thenReturn(users);

    ResponseEntity<List<User>> responseEntity = londonUsersController.getLondonUsers();

    assertThat("Controller returns empty list", responseEntity.getBody(), is(users));
  }

  @Test
  public void givenMultipleMockedUsers_GetLondonUsers_ReturnsMultipleUsersInList() {

    User userJohn = new User(
        1,
        "John",
        "Smith",
        "email@domain.com",
        "0.0.0.0",
        123.456,
        654.321);

    List<User> users = new ArrayList<>();
    users.add(userJohn);
    users.add(userJohn);
    users.add(userJohn);

    when(usersFromCityService.getUsersFromCity("London")).thenReturn(users);

    ResponseEntity<List<User>> responseEntity = londonUsersController.getLondonUsers();

    assertThat("Returned list has multiple users in it", responseEntity.getBody().size(), is(3));
    assertThat("Controller returns empty list", responseEntity.getBody(), is(users));
  }
}