package com.dev.bookstore.mappers;

public interface Mapper<A, B> {
    B toDto(A entity);

    A toEntity(B dto);
}
