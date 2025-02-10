package com.dev.bookstore.controllers;

import com.dev.bookstore.TestDataUtil;
import com.dev.bookstore.domain.dto.AuthorDto;
import com.dev.bookstore.domain.entities.AuthorEntity;
import com.dev.bookstore.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        when(authorService.create(any(AuthorEntity.class)))
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
    public void testThatCreateAuthorReturnHTTP400WhenIllegalArgumentExceptionIsThrown() throws Exception {
        when(authorService.create(any())).thenThrow(IllegalArgumentException.class);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();

        String content = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(AUTHORS_BASED_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
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
        when(authorService.list()).thenReturn(List.of(
                TestDataUtil.expectedTestAuthorEntity(null)
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
    }

    @Test
    public void testThatReadOneAuthorReturnsHTTP404WhenAuthorNotFoundInTheDatabase() throws Exception {
        when(authorService.get(any())).thenReturn(null);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(AUTHORS_BASED_URL + "/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatReadOneAuthorReturnsHTTP200AndAuthorWhenAuthorFoundInTheDatabase() throws Exception {
        when(authorService.get(any())).thenReturn(TestDataUtil.expectedTestAuthorEntity(999L));

        AuthorDto expected = TestDataUtil.expectedTestAuthorDto();
        expected.setId(999L);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(AUTHORS_BASED_URL + "/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expected.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(expected.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expected.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(expected.getImage()));
    }

    @Test
    public void testThatFullUpdateReturnsHTTP200AndUpdatedAuthorOnSuccessfulCall() throws Exception {
        when(authorService.fullUpdate(any(), any()))
                .thenAnswer(AdditionalAnswers.returnsSecondArg());

        AuthorDto authorToUpdate = TestDataUtil.updateTestAuthorDto(999L);
        String content = objectMapper.writeValueAsString(authorToUpdate);

        AuthorDto expected = TestDataUtil.expectedUpdatedTestAuthorDto(999L);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put(AUTHORS_BASED_URL + "/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expected.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(expected.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expected.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(expected.getImage()));
    }

    @Test
    public void testThatFullUpdateReturnsHTTP400WhenIllegalStateExceptionIsThrown() throws Exception {
        when(authorService.fullUpdate(any(), any()))
                .thenThrow(IllegalStateException.class);

        AuthorDto authorToUpdate = TestDataUtil.updateTestAuthorDto(999L);
        String content = objectMapper.writeValueAsString(authorToUpdate);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put(AUTHORS_BASED_URL + "/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHTTP400WhenIllegalStateExceptionIsThrown() throws Exception {
        when(authorService.partialUpdate(any(), any()))
                .thenThrow(IllegalStateException.class);

        AuthorDto authorToUpdate = TestDataUtil.partialUpdateTestAuthorDto(999L);
        String content = objectMapper.writeValueAsString(authorToUpdate);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch(AUTHORS_BASED_URL + "/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHTTP200AndUpdatedAuthor() throws Exception {
        when(authorService.partialUpdate(any(), any()))
                .thenReturn(TestDataUtil.expectedPartialUpdatedTestAuthorEntity(999L));

        AuthorDto authorToPartialUpdate = TestDataUtil.partialUpdateTestAuthorDto(999L);
        String content = objectMapper.writeValueAsString(authorToPartialUpdate);

        AuthorDto expected = TestDataUtil.expectedPartialUpdatedTestAuthorDto(999L);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch(AUTHORS_BASED_URL + "/999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expected.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expected.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(expected.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expected.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image").value(expected.getImage()));
    }
}
