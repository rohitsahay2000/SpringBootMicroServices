package com.rohit.moviecatalogservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.rohit.moviecatalogservice.model.CatalogItem;
import com.rohit.moviecatalogservice.model.Rating;
import com.rohit.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private WebClient.Builder builder;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

		UserRating userRatings = userRatingInfo.getUserRating(userId);

		List<Rating> ratingList = userRatings.getRatingList();
		List<CatalogItem> catalogItems = new ArrayList<CatalogItem>();

		for (Rating rating : ratingList) {
			catalogItems.add(movieInfo.getCatalogItem(rating));
		}

		return catalogItems;
	}

}
