// New component for CMPE 273 Assignment : AuthorsDto.java

package edu.sjsu.cmpe.library.dto;

import edu.sjsu.cmpe.library.domain.Author;

public class AuthorsDto {
	
	private Author[] authors;

	public Author[] getAuthors() {
		return authors;
	}

	public void setAuthors(Author[] authors) {
		this.authors = authors;
	}

	public AuthorsDto(Author[] authors) {
		super();
		this.authors = authors;
	}

}
