package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskMapperTestSuite {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void mapToTask() {
        // Given
        TaskDto taskDto = new TaskDto(5L, "sometitle", "somecontent");

        // When
        Task task = taskMapper.mapToTask(taskDto);

        // Then
        assertEquals("somecontent", task.getContent());
    }

    @Test
    public void mapToTaskDtoWithoutReturnTaskDto() {
        // Given
        Task task = new Task(5L, "sometitle", "somecontent");

        // When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        // Then
        assertEquals("somecontent", taskDto.getContent());
    }

    @Test
    public void mapToTaskDtoReturnList() {
        // Given
        Task task1 = new Task(5L, "sometitle", "somecontent");
        Task task2 = new Task(7L, "sometitle2", "somecontent2");

        // When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDto(List.of(task1, task2));

        // Then
        assertEquals(2, taskDtos.size());
    }
}