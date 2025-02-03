package com.dev.bookstore.controllers;

import com.dev.bookstore.domain.dto.AuthorDto;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.mappers.impl.AuthorMapper;
import com.dev.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
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
    public AuthorDto createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorEntity author = authorMapper.toEntity(authorDto);
        return authorMapper.toDto(authorService.save(author));
    }
}
