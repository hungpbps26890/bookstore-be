package com.dev.bookstore.services.impl;

import com.dev.bookstore.TestDataUtil;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthorServiceImplTest {

    private final AuthorServiceImpl underTest;

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImplTest(AuthorServiceImpl authorService, AuthorRepository authorRepository) {
        this.underTest = authorService;
        this.authorRepository = authorRepository;
    }

    @Transactional
    @Test
    public void testThatSavePersistsTheAuthorInTheDatabase() {
        AuthorEntity savedAuthor = underTest.save(TestDataUtil.createTestAuthorEntity());

        Long id = savedAuthor.getId();

        assertThat(id).isNotNull();

        AuthorEntity expected = TestDataUtil.expectedTestAuthorEntity(id);

        assertThat(savedAuthor).isEqualTo(expected);

        AuthorEntity recalledAuthor = authorRepository.findById(id).orElse(null);
        assertThat(recalledAuthor).isNotNull();
        assertThat(recalledAuthor).isEqualTo(expected);
    }

    @Test
    public void testThatListReturnsEmptyListWhenNoAuthorsInTheDatabase() {
        List<AuthorEntity> result = underTest.list();

        assertThat(result).isEmpty();
    }

    @Transactional
    @Test
    public void testThatListReturnsAuthorsWhenAuthorsInTheDatabase() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());

        List<AuthorEntity> expected = List.of(savedAuthor);

        List<AuthorEntity> result = underTest.list();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void testThatGetReturnsNullWhenAuthorNotFoundInTheDatabase() {
        AuthorEntity result = underTest.get(999L);
        assertThat(result).isNull();
    }

    @Transactional
    @Test
    public void testThatGetReturnsAuthorWhenAuthorFoundInTheDatabase() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());

        AuthorEntity result = underTest.get(savedAuthor.getId());

        assertThat(result).isEqualTo(savedAuthor);
    }
}
