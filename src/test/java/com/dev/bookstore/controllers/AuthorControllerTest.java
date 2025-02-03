package com.dev.bookstore.controllers;

import com.dev.bookstore.TestDataUtil;
import com.dev.bookstore.domain.dto.AuthorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorControllerTest {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateAuthorReturnsHttpStatus201Created() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();

        String content = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorReturnsCreatedAuthor() throws Exception {

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();

        String content = objectMapper.writeValueAsString(authorDto);

        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        AuthorDto expected = TestDataUtil.expectedTestAuthorDto();

        response
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expected.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(expected.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expected.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(expected.getImage()));
    }
}
