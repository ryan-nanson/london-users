package com.ryan.londonusers.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ryan.londonusers.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UsersWithinRadiusServiceTest {

  private RestTemplate restTemplate;
  private UsersWithinRadiusService usersWithinRadiusService;

  @Before
  public void setUp() {
    restTemplate = mock(RestTemplate.class);
    usersWithinRadiusService = new UsersWithinRadiusService(restTemplate);
  }

  @Test
  public void givenUserAtSameLongLatAsPoint_GetUsersWithinRadius_ReturnsTheUser() {

    User userWithinRadius = new User(
        1,
        "John",
        "Smith",
        "email@domain.com",
        "0.0.0.0",
        123.456,
        654.321);

    List<User> expectedUsers = new ArrayList<>();
    expectedUsers.add(userWithinRadius);

    User[] users = new User[1];
    users[0] = userWithinRadius;

    ResponseEntity<User[]> responseEntity = new ResponseEntity<>(users, HttpStatus.OK);

    when(restTemplate.exchange("http://bpdts-test-app.herokuapp.com/users", HttpMethod.GET, null, User[].class))
        .thenReturn(responseEntity);

    List<User> actualUsers = usersWithinRadiusService.getUsersWithinRadius(123.456, 654.321, 1.0);

    verify(restTemplate, times(1))
        .exchange("http://bpdts-test-app.herokuapp.com/users", HttpMethod.GET, null, User[].class);

    assertThat("If distance between user and point is less than radius, user is returned",
        actualUsers, is(expectedUsers));
  }

  @Test
  public void givenTheQueenReturnedAndLondonLongLatGiven_GetUsersWithinRadius_ReturnsTheQueen() {

    // User with long, lat of Buckingham Palace
    User theQueen = new User(
        1,
        "Elizabeth",
        "Windsor",
        "liz_windsor@bucks.com",
        "0.0.0.0",
        51.501401,
        -0.142159);

    List<User> expectedUsers = new ArrayList<>();
    expectedUsers.add(theQueen);

    User[] users = new User[1];
    users[0] = theQueen;

    ResponseEntity<User[]> responseEntity = new ResponseEntity<>(users, HttpStatus.OK);

    when(restTemplate.exchange("http://bpdts-test-app.herokuapp.com/users", HttpMethod.GET, null, User[].class))
        .thenReturn(responseEntity);

    // Call on London Long Lat with radius of 50 miles (in metres)
    List<User> actualUsers = usersWithinRadiusService.getUsersWithinRadius(51.507222, -0.1275, 80467.2);

    verify(restTemplate, times(1))
        .exchange("http://bpdts-test-app.herokuapp.com/users", HttpMethod.GET, null, User[].class);

    assertThat("If London long lat is given and the queen is the only user, she should be returned",
        actualUsers, is(expectedUsers));
  }

  @Test
  public void givenNewcastleUserReturnedAndLondonLongLatGiven_GetUsersWithinRadius_ReturnsEmptyList() {

    // User with long, lat of Newcastle
    User jordyUser = new User(
        1,
        "Declan",
        "Donnelly",
        "dec@antanddec.com",
        "0.0.0.0",
        54.978250,
        -1.617781);

    User[] users = new User[1];
    users[0] = jordyUser;

    ResponseEntity<User[]> responseEntity = new ResponseEntity<>(users, HttpStatus.OK);

    when(restTemplate.exchange("http://bpdts-test-app.herokuapp.com/users", HttpMethod.GET, null, User[].class))
        .thenReturn(responseEntity);

    // Call on London Long Lat with radius of 50 miles (in metres)
    List<User> actualUsers = usersWithinRadiusService.getUsersWithinRadius(51.507222, -0.1275, 80467.2);

    verify(restTemplate, times(1))
        .exchange("http://bpdts-test-app.herokuapp.com/users", HttpMethod.GET, null, User[].class);

    assertThat("If London long lat is given and a newcastle user is the only user, an empty list should be returned",
        actualUsers, is(new ArrayList()));
  }
}