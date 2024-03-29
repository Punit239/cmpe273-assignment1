package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Book;

@JsonPropertyOrder(alphabetic = true)
public class BookDto extends LinksDto {
    private Book book;

    /**
     * @param book
     */
    public BookDto(Book book) {
	super();
	this.book = book;
    }

    //**** CMPE 273 Assignment 1 BEG ****
    
    public BookDto() {
	super();
	}
    
    //**** CMPE 273 Assignment 1 END ****    
    
    /**
     * @return the book
     */
    public Book getBook() {
	return book;
    }

    /**
     * @param book
     *            the book to set
     */
    public void setBook(Book book) {
	this.book = book;
    }
}
