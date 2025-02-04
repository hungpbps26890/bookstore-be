package com.dev.bookstore.services.impl;

import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.repositories.AuthorRepository;
import com.dev.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
