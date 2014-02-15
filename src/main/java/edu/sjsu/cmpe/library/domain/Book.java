package edu.sjsu.cmpe.library.domain;

//**** CMPE 273 Assignment 1 BEG ****

import java.awt.List;
import java.util.ArrayList;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "isbn", "title", "publication-date", "language", "num-pages", "status","reviews", "authors"})

//**** CMPE 273 Assignment 1 END ****

public class Book {

	private long isbn;
    private String title;

    // add more fields here
    
    //**** CMPE 273 Assignment 1 BEG ****
    
	@JsonProperty("publication-date")
	@NotEmpty    
    private String publicationDate;
	
    private String language;
    
	@JsonProperty("num-pages")
	@NotEmpty   
    private long numPages;
	
    private String status;
    
    private ArrayList<Review> reviews = new ArrayList();
    
    private Author[] authors;
    
    //**** CMPE 273 Assignment 1 END ****

    /**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

    /**
     * @param isbn
     *            the isbn to set
     */
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    //**** CMPE 273 Assignment 1 BEG ****
    
    public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public long getNumPages() {
		return numPages;
	}

	public void setNumPages(long numPages) {
		this.numPages = numPages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}

	public Author[] getAuthors() {
		return authors;
	}

	public void setAuthors(Author[] authors) {
		this.authors = authors;
	}
	
	public Author getAuthorById(int id){
		
		return this.authors[id];
		
	}
	
    //**** CMPE 273 Assignment 1 END ****	

}
