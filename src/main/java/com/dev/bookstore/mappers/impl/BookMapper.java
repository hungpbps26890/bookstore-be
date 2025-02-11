package com.dev.bookstore.mappers.impl;

import com.dev.bookstore.domain.dto.AuthorSummaryDto;
import com.dev.bookstore.domain.dto.BookDto;
import com.dev.bookstore.domain.dto.BookSummaryDto;
import com.dev.bookstore.domain.dto.BookUpdateRequestDto;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.entities.BookEntity;
import com.dev.bookstore.domain.requests.AuthorSummary;
import com.dev.bookstore.domain.requests.BookSummary;
import com.dev.bookstore.domain.requests.BookUpdateRequest;
import com.dev.bookstore.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper implements Mapper<BookEntity, BookDto> {

    private final ModelMapper modelMapper;

    private final AuthorMapper authorMapper;

    @Override
    public BookDto toDto(BookEntity entity) {
        return modelMapper.map(entity, BookDto.class);
    }

    @Override
    public BookEntity toEntity(BookDto dto) {
        return modelMapper.map(dto, BookEntity.class);
    }

    public BookEntity bookSummaryToBookEntity(BookSummary bookSummary, AuthorEntity author) {
        return BookEntity.builder()
                .isbn(bookSummary.getIsbn())
                .title(bookSummary.getTitle())
                .description(bookSummary.getDescription())
                .image(bookSummary.getImage())
                .author(author)
                .build();
    }

    public BookSummary toBookSummary(BookSummaryDto bookSummaryDto) {
        AuthorSummary authorSummary = authorMapper.toAuthorSummary(bookSummaryDto.getAuthor());

        return BookSummary.builder()
                .isbn(bookSummaryDto.getIsbn())
                .title(bookSummaryDto.getTitle())
                .description(bookSummaryDto.getDescription())
                .image(bookSummaryDto.getImage())
                .author(authorSummary)
                .build();
    }

    public BookSummaryDto toBookSummaryDto(BookEntity entity) {
        AuthorSummaryDto authorSummaryDto = authorMapper.toAuthorSummaryDto(entity.getAuthor());

        return BookSummaryDto.builder()
                .isbn(entity.getIsbn())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .image(entity.getImage())
                .author(authorSummaryDto)
                .build();
    }

    public BookUpdateRequest toBookUpdateRequest(BookUpdateRequestDto bookUpdateRequestDto) {
        return modelMapper.map(bookUpdateRequestDto, BookUpdateRequest.class);
    }
}
