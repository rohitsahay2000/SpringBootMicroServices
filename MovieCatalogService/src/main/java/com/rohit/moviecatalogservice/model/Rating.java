package com.rohit.moviecatalogservice.model;

public class Rating {

	String movieId;
	Integer rating;
	
	public Rating() {
		// TODO Auto-generated constructor stub
	}
	
	public Rating(String movieId, Integer rating) {
		this.movieId=movieId;
		this.rating=rating;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
