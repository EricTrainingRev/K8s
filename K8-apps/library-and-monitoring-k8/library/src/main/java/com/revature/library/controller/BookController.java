package com.revature.library.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.library.entity.Book;
import com.revature.library.service.BookService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
public class BookController {

	private static final Logger logger = LoggerFactory.getLogger("com.revature.library.controller");

    @Autowired
    private BookService bookService;
    
    // by making the method return a Response Entity we can choose our status code and determine the body of the response
    @GetMapping("/")
    @Timed(value = "index.time", description = "Time taken to return the welcome message")
    @Counted(value = "index.count", description = "Number of index calls")
    public ResponseEntity<String> index(){
        logger.info("index() called");
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Welcome to the Library!");
    }

    @GetMapping("/book")
    @Timed(value = "getAllBooks.time", description = "Time taken to return the book collection")
    @Counted(value = "getAllBooks.count", description = "Number of getAllBooks calls")
    public ResponseEntity<List<Book>> getAllBooks(){
        logger.info("getAllBooks() called");
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @DeleteMapping("/book/{id}")
    @Timed(value = "removeBook.time", description = "Time taken to return the results of the removeBook operation")
    @Counted(value = "removeBook.count", description = "Number of removeBook calls")
    public ResponseEntity<String> removeBook(@PathVariable int id){
        logger.info("removeBook({}) called",id);
        if(bookService.removeBook(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Book has been removed from the library");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found: please try again");
        } 
        
    }

    @PostMapping("/book")
    // @Timed(value = "addBook.time", description = "Time taken to return the results of the addBook operation")
    // @Counted(value = "addBook.count", description = "Number of addBook calls")
    // these aspects mess with my metrics: no bueno
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        logger.info("addBook({}) called", book);
        Optional<Book> result = bookService.addBook(book); 
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(book);
        } 
    }

}
