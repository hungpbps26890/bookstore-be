package com.dev.bookstore.services.impl;

import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.entities.BookEntity;
import com.dev.bookstore.domain.requests.BookSummary;
import com.dev.bookstore.domain.requests.BookUpdateRequest;
import com.dev.bookstore.domain.response.BookResponse;
import com.dev.bookstore.mappers.impl.BookMapper;
import com.dev.bookstore.repositories.AuthorRepository;
import com.dev.bookstore.repositories.BookRepository;
import com.dev.bookstore.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<BookEntity> list(Long authorId) {
        return authorId != null
                ? bookRepository.findByAuthorId(authorId)
                : bookRepository.findAll();
    }

    @Override
    public BookEntity get(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new IllegalStateException("Book not found"));
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookUpdateRequest bookUpdateRequest) {
        Optional<BookEntity> foundBook = bookRepository.findById(isbn);

        return foundBook.map(existingBook -> {
            Optional.ofNullable(bookUpdateRequest.getTitle()).ifPresent(existingBook::setTitle);
            Optional.ofNullable(bookUpdateRequest.getDescription()).ifPresent(existingBook::setDescription);
            Optional.ofNullable(bookUpdateRequest.getImage()).ifPresent(existingBook::setImage);

            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new IllegalStateException("Book not found"));
    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
