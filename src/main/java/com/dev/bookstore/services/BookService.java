package com.dev.bookstore.services;

import com.dev.bookstore.domain.requests.BookSummary;
import com.dev.bookstore.domain.response.BookResponse;

public interface BookService {

    BookResponse createUpdate(String isbn, BookSummary bookSummary);
}
