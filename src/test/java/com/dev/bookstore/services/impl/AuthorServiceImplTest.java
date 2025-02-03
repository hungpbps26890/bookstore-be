package com.dev.bookstore.services.impl;

import com.dev.bookstore.TestDataUtil;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void testThatSavePersistsTheAuthorInTheDatabase() {
        AuthorEntity savedAuthor = underTest.save(TestDataUtil.createTestAuthorEntity());

        Long id = savedAuthor.getId();

        assertThat(id).isNotNull();

        assertThat(savedAuthor).isEqualTo(TestDataUtil.expectedTestAuthorEntity(id));

        AuthorEntity recalledAuthor = authorRepository.findById(id).orElse(null);
        assertThat(recalledAuthor).isNotNull();
    }
}
