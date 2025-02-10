package com.dev.bookstore.services.impl;

import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.entities.BookEntity;
import com.dev.bookstore.domain.requests.BookSummary;
import com.dev.bookstore.domain.response.BookResponse;
import com.dev.bookstore.mappers.impl.BookMapper;
import com.dev.bookstore.repositories.AuthorRepository;
import com.dev.bookstore.repositories.BookRepository;
import com.dev.bookstore.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final BookMapper bookMapper;

    @Transactional
    @Override
    public BookResponse createUpdate(String isbn, BookSummary bookSummary) {
        bookSummary.setIsbn(isbn);

        boolean exists = bookRepository.existsById(isbn);

        AuthorEntity author = authorRepository.findById(bookSummary.getAuthor().getId())
                .orElseThrow(() -> new IllegalStateException("Author not found"));

        BookEntity bookToSave = bookMapper.bookSummaryToBookEntity(bookSummary, author);

        BookEntity savedBook = bookRepository.save(bookToSave);

        return BookResponse.builder()
                .book(savedBook)
                .create(!exists)
                .build();
    }
}
