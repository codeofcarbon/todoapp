package com.codeofcarbon.todoapp.entities.dto;

import com.codeofcarbon.todoapp.entities.Task;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@ToString
public class TaskDTO {
    private final long id;
    private final String date;
    private final String name;
    private final String state;
    private final String priority;
    private final String owner;

    public static TaskDTO mapToTaskDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .date(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(task.getDate()))
                .name(task.getName())
                .state(task.getTaskState().name())
                .priority(task.getPriority().name())
                .owner(task.getOwner().getUsername())
                .build();
    }
}
