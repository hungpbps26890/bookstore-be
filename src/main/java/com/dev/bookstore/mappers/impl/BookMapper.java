package com.dev.bookstore.mappers.impl;

import com.dev.bookstore.domain.dto.BookDto;
import com.dev.bookstore.domain.entities.BookEntity;
import com.dev.bookstore.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper implements Mapper<BookEntity, BookDto> {

    private final ModelMapper modelMapper;

    @Override
    public BookDto toDto(BookEntity entity) {
        return modelMapper.map(entity, BookDto.class);
    }

    @Override
    public BookEntity toEntity(BookDto dto) {
        return modelMapper.map(dto, BookEntity.class);
    }
}
