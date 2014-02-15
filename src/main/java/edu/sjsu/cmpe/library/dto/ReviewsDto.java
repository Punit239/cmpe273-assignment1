// New component for CMPE 273 Assignment : ReviewsDto.java

package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;

import edu.sjsu.cmpe.library.domain.Review;

public class ReviewsDto extends LinksDto {
	
	private ArrayList<Review> reviews = new ArrayList();

	public ReviewsDto(ArrayList<Review> reviews) {
		super();
		this.reviews = reviews;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

}
