// New component for CMPE 273 Assignment : Review.java

package edu.sjsu.cmpe.library.domain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class Review {
	
	private long id;
	
	@NotEmpty
	private String comment;
	
	@NotNull
	@Min(1)
	private long rating;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public long getRating() {
		return rating;
	}
	public void setRating(long rating) {
		this.rating = rating;
	}

}
