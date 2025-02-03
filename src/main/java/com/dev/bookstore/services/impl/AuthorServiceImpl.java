package com.dev.bookstore.services.impl;

import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.repositories.AuthorRepository;
import com.dev.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorEntity save(AuthorEntity author) {
        return authorRepository.save(author);
    }
}
