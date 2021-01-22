package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    void getTasks() throws Exception {
        // Given
        List<TaskDto> taskDtos = List.of(new TaskDto(0L, "title0", "content0"),new TaskDto(1L, "title1", "content1"));
        when(taskController.getTasks()).thenReturn(taskDtos);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("title0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("content0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("title1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("content1")));
    }

    @Test
    void getTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(0L, "title0", "content0");
        when(taskController.getTask(0L)).thenReturn(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/task/getTask?taskId=0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("title0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("content0")));
    }

    @Test
    void deleteTask() throws Exception {
        // Given
        doNothing().when(taskController).deleteTask(0L);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/task/deleteTask?taskId=0")
                        .contentType(MediaType.APPLICATION_JSON));
        verify(taskController, times(1)).deleteTask(0L);
    }

    @Test
    void updateTask() throws Exception {
        // Given
        TaskDto inputTaskDto = new TaskDto(0L, "title0", "content0");
        TaskDto outputTaskDto = new TaskDto(1L, "title1", "content1");
        when(taskController.updateTask(any())).thenReturn(outputTaskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(inputTaskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/task/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("title1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("content1")));
    }

    @Test
    void createTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(0L, "title0", "content0");
        doNothing().when(taskController).createTask(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/task/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent));
        verify(taskController, times(1)).createTask(any());
    }
}
