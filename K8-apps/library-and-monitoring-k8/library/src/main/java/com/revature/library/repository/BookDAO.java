package com.revature.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.library.entity.Book;

public interface BookDAO extends JpaRepository<Book, Integer> {
    
}
