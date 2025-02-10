package com.dev.bookstore.domain.response;

import com.dev.bookstore.domain.entities.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private BookEntity book;
    private boolean create;
}
