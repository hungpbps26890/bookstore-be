package com.dev.bookstore.services.impl;

import com.dev.bookstore.TestDataUtil;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.requests.AuthorUpdateRequest;
import com.dev.bookstore.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
    public void testThatCreatePersistsTheAuthorInTheDatabase() {
        AuthorEntity savedAuthor = underTest.create(TestDataUtil.createTestAuthorEntity());

        Long id = savedAuthor.getId();

        assertThat(id).isNotNull();

        AuthorEntity expected = TestDataUtil.expectedTestAuthorEntity(id);

        assertThat(savedAuthor).isEqualTo(expected);

        AuthorEntity recalledAuthor = authorRepository.findById(id).orElse(null);
        assertThat(recalledAuthor).isNotNull();
        assertThat(recalledAuthor).isEqualTo(expected);
    }

    @Test
    public void testThatCreateAuthorWithIdThrowsAnIllegalArgumentException() {
        assertThatThrownBy(() -> {
            AuthorEntity existingAuthor = TestDataUtil.createTestAuthorEntity();
            existingAuthor.setId(999L);

            underTest.create(existingAuthor);
        }).isInstanceOf(IllegalArgumentException.class);

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

    @Transactional
    @Test
    public void testThatFullUpdateSuccessfulUpdatesTheAuthorInDatabase() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());
        Long existingAuthorId = savedAuthor.getId();

        AuthorEntity authorToUpdate = TestDataUtil.updateTestAuthorEntity(existingAuthorId);

        AuthorEntity result = underTest.fullUpdate(existingAuthorId, authorToUpdate);

        AuthorEntity expected = TestDataUtil.expectedUpdateTestAuthorEntity(existingAuthorId);

        assertThat(result).isEqualTo(expected);

        AuthorEntity recalledAuthor = authorRepository.findById(existingAuthorId).orElse(null);
        assertThat(recalledAuthor).isNotNull();
        assertThat(recalledAuthor).isEqualTo(expected);
    }

    @Test
    public void testThatFullUpdateThrowsIllegalStateExceptionWhenAuthorNotFoundInTheDatabase() {
        assertThatThrownBy(() -> {
            Long nonExistingAuthorId = 999L;
            AuthorEntity authorToUpdate = TestDataUtil.updateTestAuthorEntity(nonExistingAuthorId);

            underTest.fullUpdate(nonExistingAuthorId, authorToUpdate);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void testThatPartialUpdateThrowsIllegalStateExceptionWhenAuthorNotFoundInTheDatabase() {
        assertThatThrownBy(() -> {
            Long nonExistingAuthorId = 999L;
            AuthorEntity authorToUpdate = TestDataUtil.updateTestAuthorEntity(nonExistingAuthorId);

            underTest.fullUpdate(nonExistingAuthorId, authorToUpdate);
        }).isInstanceOf(IllegalStateException.class);
    }

    @Transactional
    @Test
    public void testThatPartialUpdateAuthorDoesNotUpdateWhenAllValuesOfRequestAreNull() {
        AuthorEntity existingAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());

        AuthorEntity result = underTest.partialUpdate(existingAuthor.getId(), new AuthorUpdateRequest());

        assertThat(result).isEqualTo(existingAuthor);
    }

    @Transactional
    @Test
    public void testThatPartialUpdateAuthorUpdatesAuthorName() {
        AuthorEntity existingAuthor = TestDataUtil.createTestAuthorEntity();

        String newName = "Partial Updated Author Name";

        AuthorUpdateRequest authorToUpdate = AuthorUpdateRequest.builder()
                .name(newName)
                .build();

        AuthorEntity expectedAuthor = AuthorEntity.builder()
                .name(newName)
                .age(existingAuthor.getAge())
                .description(existingAuthor.getDescription())
                .image(existingAuthor.getImage())
                .build();

        assertThatAuthorPartialUpdateIsUpdated(
                existingAuthor,
                expectedAuthor,
                authorToUpdate
        );
    }

    @Transactional
    @Test
    public void testThatPartialUpdateAuthorUpdatesAuthorAge() {
        AuthorEntity existingAuthor = TestDataUtil.createTestAuthorEntity();

        Integer newAge = 50;

        AuthorUpdateRequest authorToUpdate = AuthorUpdateRequest.builder()
                .age(newAge)
                .build();

        AuthorEntity expectedAuthor = AuthorEntity.builder()
                .name(existingAuthor.getName())
                .age(newAge)
                .description(existingAuthor.getDescription())
                .image(existingAuthor.getImage())
                .build();

        assertThatAuthorPartialUpdateIsUpdated(
                existingAuthor,
                expectedAuthor,
                authorToUpdate
        );
    }

    @Transactional
    @Test
    public void testThatPartialUpdateAuthorUpdatesAuthorDescription() {
        AuthorEntity existingAuthor = TestDataUtil.createTestAuthorEntity();

        String newDescription = "Partial Updated Author Description";

        AuthorUpdateRequest authorToUpdate = AuthorUpdateRequest.builder()
                .description(newDescription)
                .build();

        AuthorEntity expectedAuthor = AuthorEntity.builder()
                .name(existingAuthor.getName())
                .age(existingAuthor.getAge())
                .description(newDescription)
                .image(existingAuthor.getImage())
                .build();

        assertThatAuthorPartialUpdateIsUpdated(
                existingAuthor,
                expectedAuthor,
                authorToUpdate
        );
    }

    @Transactional
    @Test
    public void testThatPartialUpdateAuthorUpdatesAuthorImage() {
        AuthorEntity existingAuthor = TestDataUtil.createTestAuthorEntity();

        String newImage = "partial-update-author-image.jpg";

        AuthorUpdateRequest authorToUpdate = AuthorUpdateRequest.builder()
                .image(newImage)
                .build();

        AuthorEntity expectedAuthor = AuthorEntity.builder()
                .name(existingAuthor.getName())
                .age(existingAuthor.getAge())
                .description(existingAuthor.getDescription())
                .image(newImage)
                .build();

        assertThatAuthorPartialUpdateIsUpdated(
                existingAuthor,
                expectedAuthor,
                authorToUpdate
        );
    }

    private void assertThatAuthorPartialUpdateIsUpdated(
            AuthorEntity existingAuthor,
            AuthorEntity expectedAuthor,
            AuthorUpdateRequest authorToUpdate
    ) {
        // Save an existing Author
        AuthorEntity savedExistingAuthor = authorRepository.save(existingAuthor);
        Long existingAuthorId = savedExistingAuthor.getId();

        // Update the Author
        AuthorEntity result = underTest.partialUpdate(
                existingAuthorId,
                authorToUpdate
        );

        // Set up the expected Author
        expectedAuthor.setId(existingAuthorId);

        assertThat(result).isEqualTo(expectedAuthor);

        AuthorEntity recalledAuthor = authorRepository.findById(existingAuthorId).orElse(null);
        assertThat(recalledAuthor).isNotNull();
        assertThat(recalledAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    public void testThatDeleteDeletesAnExistingAuthorInTheDatabase() {
        AuthorEntity savedAuthor = authorRepository.save(TestDataUtil.createTestAuthorEntity());
        Long existingAuthorId = savedAuthor.getId();

        underTest.delete(existingAuthorId);

        assertThat(authorRepository.existsById(existingAuthorId)).isFalse();
    }

    @Test
    public void testThatDeleteDeletesAnNonExistingAuthorInTheDatabase() {
        Long nonExistingAuthorId = 999L;

        underTest.delete(nonExistingAuthorId);

        assertThat(authorRepository.existsById(nonExistingAuthorId)).isFalse();
    }
}
