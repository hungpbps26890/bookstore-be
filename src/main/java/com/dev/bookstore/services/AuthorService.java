package com.dev.bookstore.services;

import com.dev.bookstore.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {

    AuthorEntity save(AuthorEntity author);

    List<AuthorEntity> list();

    AuthorEntity get(Long id);
}
