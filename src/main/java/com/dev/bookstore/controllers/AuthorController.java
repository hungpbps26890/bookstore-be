package com.dev.bookstore.controllers;

import com.dev.bookstore.domain.dto.AuthorDto;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.mappers.impl.AuthorMapper;
import com.dev.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        try {
            AuthorEntity authorToCreate = authorMapper.toEntity(authorDto);
            AuthorDto createdAuthor = authorMapper.toDto(authorService.create(authorToCreate));
            return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<AuthorDto> readManyAuthors() {
        return authorService.list()
                .stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> readOneAuthor(@PathVariable("id") Long id) {
        return Optional.ofNullable(authorService.get(id))
                .map(foundAuthor ->
                        new ResponseEntity<>(authorMapper.toDto(foundAuthor), HttpStatus.OK)
                )
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto authorDto
    ) {
        try {
            AuthorEntity authorToUpdate = authorMapper.toEntity(authorDto);
            AuthorEntity updatedAuthor = authorService.fullUpdate(id, authorToUpdate);
            return new ResponseEntity<>(authorMapper.toDto(updatedAuthor), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
