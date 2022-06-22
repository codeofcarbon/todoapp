package com.codeofcarbon.todoapp.controllers;

import com.codeofcarbon.todoapp.entities.Task;
import com.codeofcarbon.todoapp.entities.dto.TaskDTO;
import com.codeofcarbon.todoapp.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> showTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> addTask(@RequestBody @Valid Task task) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.addTask(task));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> changeToComplete(@PathVariable("id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.updateToComplete(id));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<HttpStatus> removeTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/tasks/remove")
    public ResponseEntity<Void> removeCompleted() {
        taskService.deleteAllCompleted();
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
