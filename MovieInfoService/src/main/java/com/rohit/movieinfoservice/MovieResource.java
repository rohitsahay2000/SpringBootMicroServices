package com.rohit.movieinfoservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${api.key}")
	private String apiKey;
	
	
	@RequestMapping("/{movieId}")
	public Movie getMovieInfo(@PathVariable("movieId") String movieId) {	
		
		String url = "https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+apiKey;
		
		System.out.println(url);
		
		MovieSummary movieSummary = restTemplate.getForObject(url, MovieSummary.class);
		
		return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
	}

}
