package com.dev.bookstore.services.impl;

import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.requests.AuthorUpdateRequest;
import com.dev.bookstore.repositories.AuthorRepository;
import com.dev.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorEntity create(AuthorEntity author) {
        if (author.getId() != null) throw new IllegalArgumentException("Cannot create new author with id");

        return authorRepository.save(author);
    }

    @Override
    public List<AuthorEntity> list() {
        return authorRepository.findAll();
    }

    @Override
    public AuthorEntity get(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public AuthorEntity fullUpdate(Long id, AuthorEntity author) {
        if (!authorRepository.existsById(id)) {
            throw new IllegalStateException("Author not found");
        }

        author.setId(id);

        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public AuthorEntity partialUpdate(Long id, AuthorUpdateRequest request) {
        Optional<AuthorEntity> foundAuthor = authorRepository.findById(id);

        return foundAuthor.map(existingAuthor -> {
            Optional.ofNullable(request.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(request.getAge()).ifPresent(existingAuthor::setAge);
            Optional.ofNullable(request.getDescription()).ifPresent(existingAuthor::setDescription);
            Optional.ofNullable(request.getImage()).ifPresent(existingAuthor::setImage);

            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new IllegalStateException("Author not found"));
    }
}
