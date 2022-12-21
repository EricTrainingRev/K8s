package com.revature.library.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.library.entity.Book;
import com.revature.library.repository.BookDAO;

@Service
public class BookServiceImp implements BookService{

    private static final Logger logger = LoggerFactory.getLogger("com.revature.library.service.BookServiceImp");

    @Autowired
    private BookDAO bookDAO;

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookDAO.findAll();
        return books;
    }

    @Override
    public Optional<Book> addBook(Book book) {
        if(book.getBookId()!=0 | book.getAuthor().equals(null) | book.getTitle().equals(null)){
            return Optional.empty();
        } else {
            Book savedBook = bookDAO.save(book);
            return Optional.ofNullable(savedBook);
        } 
    }

    @Override
    public boolean removeBook(int id) {
        if(bookDAO.findById(id).isPresent()){
            bookDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
