package com.dev.bookstore.controllers;

import com.dev.bookstore.domain.dto.BookSummaryDto;
import com.dev.bookstore.domain.dto.BookUpdateRequestDto;
import com.dev.bookstore.domain.entities.BookEntity;
import com.dev.bookstore.domain.response.BookResponse;
import com.dev.bookstore.mappers.impl.BookMapper;
import com.dev.bookstore.services.BookService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @PutMapping(path = "/{isbn}")
    public ResponseEntity<BookSummaryDto> createAndFullUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookSummaryDto bookSummaryDto
    ) {
        try {
            BookResponse bookResponse = bookService.createUpdate(isbn, bookMapper.toBookSummary(bookSummaryDto));

            BookEntity savedBook = bookResponse.getBook();
            boolean isCreated = bookResponse.isCreate();

            HttpStatus responseCode = isCreated ? HttpStatus.CREATED : HttpStatus.OK;

            return new ResponseEntity<>(bookMapper.toBookSummaryDto(savedBook), responseCode);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<BookSummaryDto> readManyBooks(@Nullable @RequestParam("author") Long authorId) {
        List<BookEntity> books = bookService.list(authorId);

        return books.stream()
                .map(bookMapper::toBookSummaryDto)
                .toList();
    }

    @GetMapping(path = "/{isbn}")
    public ResponseEntity<BookSummaryDto> readOneBook(@PathVariable("isbn") String isbn) {
        try {
            BookEntity foundBook = bookService.get(isbn);
            return new ResponseEntity<>(bookMapper.toBookSummaryDto(foundBook), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(path = "/{isbn}")
    public ResponseEntity<BookSummaryDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookUpdateRequestDto bookUpdateRequestDto
    ) {
        try {
            BookEntity updatedBook = bookService.partialUpdate(isbn, bookMapper.toBookUpdateRequest(bookUpdateRequestDto));
            return new ResponseEntity<>(bookMapper.toBookSummaryDto(updatedBook), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn) {
        bookService.delete(isbn);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
