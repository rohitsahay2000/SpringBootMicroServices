package com.rohit.moviecatalogservice;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rohit.moviecatalogservice.model.Rating;
import com.rohit.moviecatalogservice.model.UserRating;

@Service
public class UserRatingInfo {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallBackUserRating")
	public UserRating getUserRating(String userId) {
		return restTemplate.getForObject("http://RatingsDataService/ratingsdata/users/" + userId, UserRating.class);
	}
	
	public UserRating getFallBackUserRating(String userId) {

		UserRating userRating = new UserRating();
		userRating.setRatingList(Arrays.asList(new Rating("0", 0)));

		return userRating;
	}


}
