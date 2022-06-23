package com.codeofcarbon.todoapp.data;

import com.codeofcarbon.todoapp.entities.*;
import com.codeofcarbon.todoapp.entities.dto.TaskDTO;

import java.time.LocalDateTime;
import java.util.*;

public class TestsData {
    public static final String NAME = "Write tests";
    public static final String NAME2 = "Write more tests";
    public static final LocalDateTime DATE = LocalDateTime.of(2022, 6, 23, 23, 0);
    public static final LocalDateTime DATE2 = LocalDateTime.of(2022, 6, 24, 1, 0);

    public static User generateSampleUser() {
        return new User(1L, "username1", "password1",
                new HashSet<>(), new HashSet<>());
    }

    public static Task generateSampleTask() {
        var user = generateSampleUser();
        return new Task(1L, DATE, NAME, TaskState.ACTIVE, Priority.HIGH, user);
    }

    public static Task generateSecondSampleTask() {
        var user = generateSampleUser();
        return new Task(2L, DATE2, NAME2, TaskState.ACTIVE, Priority.LOW, user);
    }

    public static TaskDTO getDTOFromGeneratedTask() {
        var task = generateSampleTask();
        return TaskDTO.mapToTaskDTO(task);
    }
}