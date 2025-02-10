package com.dev.bookstore.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSummary {
    private String isbn;
    private String title;
    private String description;
    private String image;
    private AuthorSummary author;
}
