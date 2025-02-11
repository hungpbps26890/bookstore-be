package com.dev.bookstore.controllers;

import com.dev.bookstore.TestDataUtil;
import com.dev.bookstore.domain.dto.AuthorSummaryDto;
import com.dev.bookstore.domain.dto.BookSummaryDto;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.entities.BookEntity;
import com.dev.bookstore.domain.response.BookResponse;
import com.dev.bookstore.mappers.impl.BookMapper;
import com.dev.bookstore.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.dev.bookstore.TestDataUtil.BOOK_ISBN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTest {

    public static final String BOOKS_BASED_URL = "/books";

    private final MockMvc mockMvc;

    @MockitoBean
    private final BookService bookService;

    private final ObjectMapper objectMapper;

    private final BookMapper bookMapper;

    @Autowired
    public BookControllerTest(MockMvc mockMvc, BookService bookService, ObjectMapper objectMapper, BookMapper bookMapper) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = objectMapper;
        this.bookMapper = bookMapper;
    }

    @Test
    public void testThatCreateFullUpdateBookReturnsHTTP201WhenBookIsCreated() throws Exception {
        assertThatBookCreatedOrUpdated(true, HttpStatus.CREATED.value());
    }

    @Test
    public void testThatCreateFullUpdateBookReturnsHTTP200WhenBookIsUpdated() throws Exception {
        assertThatBookCreatedOrUpdated(false, HttpStatus.OK.value());
    }

    @Test
    public void testThatCreateFullUpdateBookReturnHTTP400WhenAuthorDoesNotExist() throws Exception {
        when(bookService.createUpdate(any(), any())).thenThrow(IllegalStateException.class);

        AuthorSummaryDto authorSummaryDto = TestDataUtil.testAuthorSummaryDto(999L);
        BookSummaryDto bookSummaryDto = TestDataUtil.testBookSummaryDto(BOOK_ISBN, authorSummaryDto);

        String content = objectMapper.writeValueAsString(bookSummaryDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put(BOOKS_BASED_URL + "/" + BOOK_ISBN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    private void assertThatBookCreatedOrUpdated(boolean isCreated, int statusCode) throws Exception {
        String isbn = BOOK_ISBN;
        AuthorEntity author = TestDataUtil.testAuthorEntity(999L);
        BookEntity savedBook = TestDataUtil.testBookEntity(isbn, author);

        when(bookService.createUpdate(any(), any()))
                .thenReturn(BookResponse.builder().book(savedBook).create(isCreated).build());

        AuthorSummaryDto authorSummaryDto = TestDataUtil.testAuthorSummaryDto(999L);
        BookSummaryDto bookSummaryDto = TestDataUtil.testBookSummaryDto(isbn, authorSummaryDto);

        String content = objectMapper.writeValueAsString(bookSummaryDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put(BOOKS_BASED_URL + "/" + isbn)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(
                MockMvcResultMatchers.status().is(statusCode)
        );
    }

    @Test
    public void testThatReadManyBooksReturnsAListOfBooks() throws Exception {
        AuthorEntity author = TestDataUtil.testAuthorEntity(1L);
        BookEntity book = TestDataUtil.testBookEntity(BOOK_ISBN, author);

        when(bookService.list())
                .thenReturn(List.of(book));

        BookSummaryDto expected = bookMapper.toBookSummaryDto(book);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(BOOKS_BASED_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(
                        MockMvcResultMatchers.status().isOk()
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(expected.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(expected.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(expected.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value(expected.getImage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author.id").value(author.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author.name").value(author.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author.image").value(author.getImage()));
    }
}
