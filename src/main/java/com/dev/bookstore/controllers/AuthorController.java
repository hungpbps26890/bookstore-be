package com.dev.bookstore.controllers;

import com.dev.bookstore.domain.dto.AuthorDto;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.mappers.impl.AuthorMapper;
import com.dev.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorEntity authorToCreate = authorMapper.toEntity(authorDto);
        AuthorDto createdAuthor = authorMapper.toDto(authorService.save(authorToCreate));
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }
}
