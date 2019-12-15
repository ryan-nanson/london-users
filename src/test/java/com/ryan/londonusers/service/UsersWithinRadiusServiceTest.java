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
}