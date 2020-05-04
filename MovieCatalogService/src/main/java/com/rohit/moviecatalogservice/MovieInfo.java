package com.rohit.moviecatalogservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rohit.moviecatalogservice.model.CatalogItem;
import com.rohit.moviecatalogservice.model.Movie;
import com.rohit.moviecatalogservice.model.Rating;

@Service
public class MovieInfo {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@HystrixCommand(fallbackMethod = "getFallBackCatalogItem" ,
			commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
								 @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="5"),
								 @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
								 @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="5000")
								 })
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://MovieInfoService/movies/" + rating.getMovieId(), Movie.class);
		return new CatalogItem(movie.getTitle(), movie.getOverview(), rating.getRating());
	}

	public CatalogItem getFallBackCatalogItem(Rating rating) {

		return new CatalogItem("No movie", "N/A", 0);
	}


}
