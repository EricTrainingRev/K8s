package com.revature.library.service;

import java.util.List;
import java.util.Optional;

import com.revature.library.entity.Book;


public interface BookService {

    public abstract List<Book> getAllBooks();
    public abstract Optional<Book> addBook(Book book);
    public abstract boolean removeBook(int id);

}
