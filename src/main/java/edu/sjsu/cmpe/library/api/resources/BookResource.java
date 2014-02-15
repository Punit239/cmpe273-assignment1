package edu.sjsu.cmpe.library.api.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.dropwizard.jersey.params.LongParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.library.domain.Author;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.dto.AuthorDto;
import edu.sjsu.cmpe.library.dto.AuthorsDto;
import edu.sjsu.cmpe.library.dto.BookDto;
import edu.sjsu.cmpe.library.dto.LinkDto;
import edu.sjsu.cmpe.library.dto.ReviewDto;
import edu.sjsu.cmpe.library.dto.ReviewsDto;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;

@Path("/v1/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
	
	//**** CMPE 273 Assignment 1 BEG ****
	
	private static long author_id=1;
	private static long review_id=1;
	
	//**** CMPE 273 Assignment 1 END ****
	
    /** bookRepository instance */
    private final BookRepositoryInterface bookRepository;

    /**
     * BookResource constructor
     * 
     * @param bookRepository
     *            a BookRepository instance
     */
    public BookResource(BookRepositoryInterface bookRepository) {
	this.bookRepository = bookRepository;
    }

    //**** 2. Create Book API ****
    
    @POST
    @Timed(name = "create-book")
    public Response createBook(Book request) {
	// Store the new book in the BookRepository so that we can retrieve it.
	Book savedBook = bookRepository.saveBook(request);

	//**** CMPE 273 Assignment 1 BEG ****
	
	for (int i = 0; i < savedBook.getAuthors().length; i++){
		
		savedBook.getAuthors()[i].id = author_id;
		author_id++;
		
	}
	
	//**** CMPE 273 Assignment 1 END ****
	
	String location = "/books/" + savedBook.getIsbn();
	BookDto bookResponse = new BookDto(savedBook);
	
    bookResponse.addLink(new LinkDto("view-book", location, "GET"));
	bookResponse.addLink(new LinkDto("update-book", location, "POST"));
	
	// Add other links if needed
	
	//**** CMPE 273 Assignment 1 BEG ****
	
	bookResponse.addLink(new LinkDto("delete-book", location, "DELETE"));
	bookResponse.addLink(new LinkDto("create-review", location + "/reviews", "POST"));
	
	//**** CMPE 273 Assignment 1 END ****

	return Response.status(201).entity(bookResponse.getLinks()).build();
    }
    
    //**** 3. View Book API ****
    
    @GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public BookDto getBookByIsbn(@PathParam("isbn") LongParam isbn) {
	Book book = bookRepository.getBookByISBN(isbn.get());
	BookDto bookResponse = new BookDto(book);
	bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(),"GET"));
	bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "POST"));
	// add more links
	
	//**** CMPE 273 Assignment 1 BEG ****
	
	bookResponse.addLink(new LinkDto("delete-book", "/books/" + book.getIsbn(), "DELETE"));
	bookResponse.addLink(new LinkDto("create-review", "/books/" + book.getIsbn() + "/reviews", "POST"));
	
	if (!book.getReviews().isEmpty())
		bookResponse.addLink(new LinkDto("view-all-reviews", "/books/" + book.getIsbn() + "/reviews", "GET"));
	
	
	//**** CMPE 273 Assignment 1 END ****

	return bookResponse;
	
    }

    //**** CMPE 273 Assignment 1 BEG ****
    
    //**** 4. Delete Book API ****
    
    @DELETE
    @Path("/{isbn}")
    @Timed(name = "delete-book")
    public Response deleteBook(@PathParam("isbn") LongParam isbn) {
    	
    	BookDto bookResponse = new BookDto();
    	
        bookRepository.deleteBookByISBN(isbn.get());
        bookResponse.addLink(new LinkDto("create-book", "/books", "POST"));
    	
        return Response.status(200).entity(bookResponse.getLinks()).build();
    	
    }
    
    //**** 5. Update Book API ****
    
    @PUT
    @Path("/{isbn}")
    @Timed(name = "update-book")
    public Response updateBook(@PathParam("isbn") LongParam isbn, @QueryParam("status") String status) {
    	
    	BookDto bookResponse = new BookDto();
    	
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	book.setStatus(status);
        
        bookResponse.addLink(new LinkDto("view-book", "/books/" + book.getIsbn(), "GET"));
        bookResponse.addLink(new LinkDto("update-book","/books/" + book.getIsbn(), "PUT"));
        bookResponse.addLink(new LinkDto("delete-book","/books/" + book.getIsbn(), "DELETE"));
        bookResponse.addLink(new LinkDto("create-review","/books/" + book.getIsbn() + "/reviews", "POST"));
        
        if (!book.getReviews().isEmpty())
    		bookResponse.addLink(new LinkDto("view-all-reviews", "/books/" + book.getIsbn() + "/reviews", "GET"));
    	
    	
        return Response.status(200).entity(bookResponse.getLinks()).build();
    	
    }

    //**** 6. Create Book Review API ****
    
    @POST
    @Path("/{isbn}/reviews")
    @Timed(name = "create-review")
    public Response createReview(Review reviews, @PathParam("isbn") LongParam isbn) {
    	
    	BookDto bookResponse = new BookDto();
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	
    	reviews.setId(review_id);
    	review_id++;
    	book.getReviews().add(reviews);
    	
    	long tmp_id = review_id - 1;
    	bookResponse.addLink(new LinkDto("view-review", "/books/" + book.getIsbn() + "/reviews/" + tmp_id, "GET"));
    	
        return Response.status(201).entity(bookResponse.getLinks()).build();
    	
    }
    
    //**** 7. View Book Review API ****

    @GET
    @Path("/{isbn}/reviews/{reviewId}")
    @Timed(name = "view-book-review")
    public ReviewDto viewBookReview(@PathParam("isbn") LongParam isbn, @PathParam("reviewId") LongParam reviewId) {
    	
    	int i=0;
    	ReviewDto reviewResponse;
    	
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	
    	for (i=0; i<book.getReviews().size(); i++){
    		
    		if(book.getReviews().get(i).getId() == reviewId.get())
    			break;
    				
    	}
    	
    	reviewResponse = new ReviewDto(book.getReviews().get(i));
    	reviewResponse.addLink(new LinkDto("view-review", "/books/" + isbn.get() + "/reviews/" + reviewId.get() , "GET"));
    	
        return reviewResponse;
    	
    }
    
    //**** 8. View All Book Reviews API ****
    
    @GET
    @Path("/{isbn}/reviews")
    @Timed(name = "view-all-book-reviews")
    public ReviewsDto viewAllBookReviews(@PathParam("isbn") LongParam isbn) {
    	
    	ReviewsDto reviewsResponse;
    	
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	
    	reviewsResponse = new ReviewsDto(book.getReviews());
    	
        return reviewsResponse;
    	
    }

    //**** 9. View Book Author API ****    
    
    @GET
    @Path("/{isbn}/authors/{authorId}")
    @Timed(name = "view-book-author")
    public Response viewBookAuthor(@PathParam("isbn") LongParam isbn, @PathParam("authorId") LongParam authorId) {
    	
    	int i=0;
    	AuthorDto authorResponse;
    	
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	
    	while (book.getAuthorById(i).id != authorId.get()){
    		
    		i++;
        
    	}
    		
    	authorResponse = new AuthorDto(book.getAuthorById((int)i));
    	
    	authorResponse.addLink(new LinkDto("view-author", "/books/" + book.getIsbn() + "/authors/" + authorId.get(), "GET"));
    	
    	return Response.status(200).entity(authorResponse).build();
    	
    }
    
    //**** 10. View All Book Author API ****    
    
    @GET
    @Path("/{isbn}/authors")
    @Timed(name = "view-all-book-author")
    public AuthorsDto viewAllBookAuthor(@PathParam("isbn") LongParam isbn) {
    	
    	AuthorsDto authorsResponse;
    	
    	Book book = bookRepository.getBookByISBN(isbn.get());
    	
    	authorsResponse = new AuthorsDto(book.getAuthors());
    	
    	return authorsResponse;
    	
    }
        
    //**** CMPE 273 Assignment 1 END ****

}

