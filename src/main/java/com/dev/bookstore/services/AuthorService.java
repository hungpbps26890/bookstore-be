package com.dev.bookstore.services;

import com.dev.bookstore.domain.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {

    AuthorEntity create(AuthorEntity author);

    List<AuthorEntity> list();

    AuthorEntity get(Long id);

    AuthorEntity fullUpdate(Long id, AuthorEntity author);
}
