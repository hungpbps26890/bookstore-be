package com.dev.bookstore.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorUpdateRequest {
    private Long id;
    private String name;
    private Integer age;
    private String description;
    private String image;
}
