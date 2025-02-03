package com.dev.bookstore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    private String isbn;
    private String title;
    private String description;
    private String image;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
