package com.dev.bookstore.services;

import com.dev.bookstore.domain.entities.BookEntity;
import com.dev.bookstore.domain.requests.BookSummary;
import com.dev.bookstore.domain.requests.BookUpdateRequest;
import com.dev.bookstore.domain.response.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse createUpdate(String isbn, BookSummary bookSummary);

    List<BookEntity> list(Long authorId);

    BookEntity get(String isbn);

    BookEntity partialUpdate(String isbn, BookUpdateRequest bookUpdateRequest);

    void delete(String isbn);
}
