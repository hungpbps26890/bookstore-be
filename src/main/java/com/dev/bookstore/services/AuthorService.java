package com.dev.bookstore.services;

import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.requests.AuthorUpdateRequest;

import java.util.List;

public interface AuthorService {

    AuthorEntity create(AuthorEntity author);

    List<AuthorEntity> list();

    AuthorEntity get(Long id);

    AuthorEntity fullUpdate(Long id, AuthorEntity author);

    AuthorEntity partialUpdate(Long id, AuthorUpdateRequest request);

    void delete(Long id);
}
