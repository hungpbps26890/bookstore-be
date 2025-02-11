package com.dev.bookstore.services.impl;

import com.dev.bookstore.TestDataUtil;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.entities.BookEntity;
import com.dev.bookstore.domain.requests.AuthorSummary;
import com.dev.bookstore.domain.requests.BookSummary;
import com.dev.bookstore.domain.response.BookResponse;
import com.dev.bookstore.repositories.AuthorRepository;
import com.dev.bookstore.repositories.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dev.bookstore.TestDataUtil.BOOK_ISBN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
public class BookServiceImplTest {

    private final BookServiceImpl underTest;

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImplTest(BookServiceImpl underTest, BookRepository bookRepository, AuthorRepository authorRepository) {
        this.underTest = underTest;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatCreateUpdateThrowsIllegalStatExceptionWhenAuthorDoesNotExist() {
        AuthorSummary authorSummary = TestDataUtil.testAuthorSummary(999L);
        BookSummary bookSummary = TestDataUtil.testBookSummary(BOOK_ISBN, authorSummary);

        Assertions.assertThatThrownBy(() -> {
            underTest.createUpdate(BOOK_ISBN, bookSummary);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testThatCreateUpdateSuccessfulCreatesBookInTheDatabase() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());
        assertThat(savedAuthor).isNotNull();

        AuthorSummary authorSummary = TestDataUtil.testAuthorSummary(savedAuthor.getId());
        BookSummary bookSummary = TestDataUtil.testBookSummary(BOOK_ISBN, authorSummary);

        BookResponse savedBookResponse = underTest.createUpdate(BOOK_ISBN, bookSummary);

        BookEntity recalledBook = bookRepository.findById(BOOK_ISBN).orElse(null);
        assertThat(recalledBook).isNotNull();

        assertThat(savedBookResponse.isCreate()).isTrue();
        assertThat(recalledBook).isEqualTo(savedBookResponse.getBook());
    }

    @Test
    public void testThatCreateUpdateSuccessfulUpdatesBookInTheDatabase() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());
        assertThat(savedAuthor).isNotNull();

        BookEntity savedBook = bookRepository.save(TestDataUtil.testBookEntity(BOOK_ISBN, savedAuthor));
        assertThat(savedBook).isNotNull();

        AuthorSummary authorSummary = TestDataUtil.testAuthorSummary(savedAuthor.getId());
        BookSummary bookSummary = TestDataUtil.testUpdatedBookSummary(BOOK_ISBN, authorSummary);

        BookResponse updatedBookResponse = underTest.createUpdate(BOOK_ISBN, bookSummary);

        BookEntity recalledBook = bookRepository.findById(BOOK_ISBN).orElse(null);
        assertThat(recalledBook).isNotNull();

        assertThat(updatedBookResponse.isCreate()).isFalse();
        assertThat(recalledBook).isEqualTo(updatedBookResponse.getBook());
    }

    @Test
    public void testThatListReturnsAnEmptyListWhenNoBookInTheDatabase() {
        List<BookEntity> result = underTest.list(null);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testThatListReturnsBooksWhenBooksInTheDatabase() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());
        assertThat(savedAuthor).isNotNull();

        BookEntity savedBook = bookRepository.save(TestDataUtil.testBookEntity(BOOK_ISBN, savedAuthor));
        assertThat(savedBook).isNotNull();

        List<BookEntity> result = underTest.list(null);

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result).hasSize(1);
        Assertions.assertThat(result.get(0)).isEqualTo(savedBook);
    }

    @Test
    public void testThatListReturnsNoBookWhenTheAuthorIdDoesNotMatch() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());
        assertThat(savedAuthor).isNotNull();

        BookEntity savedBook = bookRepository.save(TestDataUtil.testBookEntity(BOOK_ISBN, savedAuthor));
        assertThat(savedBook).isNotNull();

        List<BookEntity> result = underTest.list(999L);

        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testThatListReturnsNoBookWhenTheAuthorIdDoesMatch() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());
        assertThat(savedAuthor).isNotNull();

        Long authorId = savedAuthor.getId();

        BookEntity savedBook = bookRepository.save(TestDataUtil.testBookEntity(BOOK_ISBN, savedAuthor));
        assertThat(savedBook).isNotNull();

        List<BookEntity> result = underTest.list(authorId);

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result.get(0)).isEqualTo(savedBook);
    }

    @Test
    public void testThatGetThrowsIllegalStateExceptionWhenBookNotFoundInTheDatabase() {
        Assertions.assertThatThrownBy(() -> {
            underTest.get(BOOK_ISBN);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testThatGetReturnsBookWhenTheBookIsFoundInTheDatabase() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());
        assertThat(savedAuthor).isNotNull();

        BookEntity savedBook = bookRepository.save(TestDataUtil.testBookEntity(BOOK_ISBN, savedAuthor));
        assertThat(savedBook).isNotNull();

        BookEntity result = underTest.get(BOOK_ISBN);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(savedBook);
    }
}
