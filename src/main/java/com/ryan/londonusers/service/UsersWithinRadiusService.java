package com.ryan.londonusers.service;

import com.ryan.londonusers.model.User;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.lucene.util.SloppyMath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service to return all users from a city.
 */
@Service
public class UsersWithinRadiusService {

  /**
   * Constant for the base path of the backend request url.
   */
  final private String BACKEND_URL = "http://bpdts-test-app.herokuapp.com/";

  /**
   * Logger for this service.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(UsersWithinRadiusService.class);

  /**
   * Define restTemplate.
   */
  private RestTemplate restTemplate;

  /**
   * Constructor for the service with restTemplate bean.
   * @param restTemplate used to make the backend request.
   */
  @Autowired
  public UsersWithinRadiusService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Method to get all users within a radius of a given longitude, latitude point.
   *
   * @param latitudeOfPoint - latitude of center point to get users from.
   * @param longitudeOfPoint - longitude of center point to get users from.
   * @param radius - radius in metres around the point to get users from.
   * @return list of users within radius of long lat point.
   */
  public List<User> getUsersWithinRadius(final double latitudeOfPoint, final double longitudeOfPoint, final double radius) {

    final String requestUrl = BACKEND_URL + "users";

    LOGGER.info("Making request to get all users.");
    final ResponseEntity<User[]> responseEntity =
        restTemplate.exchange(requestUrl, HttpMethod.GET, null, User[].class);


    List<User> users = Arrays.asList(responseEntity.getBody());

    LOGGER.info("Filtering results to only users within 50 miles of London.");
    return users.stream()
        .filter(user -> SloppyMath.haversinMeters(latitudeOfPoint, longitudeOfPoint, user.getLatitude(), user.getLongitude())
            <= radius)
        .collect(Collectors.toList());
  }
}