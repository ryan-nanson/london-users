package com.ryan.londonusers.service;

import com.ryan.londonusers.model.User;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service to return all users from a city.
 */
@Service
public class UsersFromCityService {

  /**
   * Constant for the base path of the backend request url.
   */
  final private String BACKEND_URL = "http://bpdts-test-app.herokuapp.com/";

  /**
   * Define restTemplate.
   */
  private RestTemplate restTemplate;

  /**
   * Constructor for the Service with restTemplate bean.
   * @param restTemplate used to make the backend request.
   */
  @Autowired
  public UsersFromCityService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Get all users in a list from a given city.
   *
   * @param city - String name of city to allow the methid to be reused for future requirements.
   * @return - List of users from given city.
   */
  public List<User> getUsersFromCity(final String city) {

    final String requestUrl = BACKEND_URL + "city/" + city + "/users";

    final ResponseEntity<User[]> responseEntity =
        restTemplate.exchange(requestUrl, HttpMethod.GET, null, User[].class);

    final User[] users = responseEntity.getBody();

    return responseEntity.getStatusCode() == HttpStatus.OK ? Arrays.asList(users) : null;
  }
}