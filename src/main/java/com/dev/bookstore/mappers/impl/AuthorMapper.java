package com.dev.bookstore.mappers.impl;

import com.dev.bookstore.domain.dto.AuthorDto;
import com.dev.bookstore.domain.dto.AuthorSummaryDto;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.domain.requests.AuthorSummary;
import com.dev.bookstore.domain.requests.AuthorUpdateRequest;
import com.dev.bookstore.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    @Override
    public AuthorDto toDto(AuthorEntity entity) {
        return modelMapper.map(entity, AuthorDto.class);
    }

    @Override
    public AuthorEntity toEntity(AuthorDto dto) {
        return modelMapper.map(dto, AuthorEntity.class);
    }

    public AuthorUpdateRequest toUpdateRequest(AuthorDto dto) {
        return modelMapper.map(dto, AuthorUpdateRequest.class);
    }

    public AuthorSummary toAuthorSummary(AuthorSummaryDto authorSummaryDto) {
        return modelMapper.map(authorSummaryDto, AuthorSummary.class);
    }

    public AuthorSummaryDto toAuthorSummaryDto(AuthorEntity entity) {
        return modelMapper.map(entity, AuthorSummaryDto.class);
    }
}
