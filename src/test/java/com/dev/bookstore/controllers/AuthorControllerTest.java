package com.dev.bookstore.controllers;

import com.dev.bookstore.TestDataUtil;
import com.dev.bookstore.domain.dto.AuthorDto;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorControllerTest {

    public static final String AUTHORS_BASED_URL = "/authors";

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @MockitoBean
    private final AuthorService authorService;

    @Autowired
    public AuthorControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authorService = authorService;
    }

    @BeforeEach
    public void beforeEach() {
        Mockito.when(authorService.save(Mockito.any(AuthorEntity.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
    }

    @Test
    public void testThatCreateAuthorReturnsHttpStatus201Created() throws Exception {

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();

        String content = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(AUTHORS_BASED_URL)
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
                        .post(AUTHORS_BASED_URL)
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

    @Test
    public void testThatListAuthorsReturnsEmptyListAndHttp200WhenNoAuthorsInTheDatabase() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(AUTHORS_BASED_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    public void testThatListAuthorsReturnsAuthorsAndHttp200WhenAuthorsInTheDatabase() throws Exception {
        Mockito.when(authorService.list()).thenReturn(List.of(
                TestDataUtil.expectedTestAuthorEntity(1L)
        ));

        AuthorDto expected = TestDataUtil.expectedTestAuthorDto();

        String expectedList = objectMapper.writeValueAsString(List.of(
                TestDataUtil.expectedTestAuthorDto()
        ));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(AUTHORS_BASED_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedList))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(expected.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(expected.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(expected.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image").value(expected.getImage()));
        ;
    }
}
