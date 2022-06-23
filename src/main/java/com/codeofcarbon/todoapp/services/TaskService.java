package com.codeofcarbon.todoapp.services;

import com.codeofcarbon.todoapp.entities.*;
import com.codeofcarbon.todoapp.entities.dto.TaskDTO;
import com.codeofcarbon.todoapp.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskDTO::mapToTaskDTO).toList();
    }

    @Transactional
    public TaskDTO addTask(Task task) {
        User taskOwner = userRepository.findById(task.getOwner().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found a task owner"));

        taskOwner.getTasks().add(task);
        task.setOwner(taskOwner);
        taskRepository.save(task);

        return TaskDTO.mapToTaskDTO(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task taskById = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found a task with this id"));
        User taskOwner = userRepository.findById(taskById.getOwner().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found a task owner"));

        taskOwner.getTasks().remove(taskById);
        taskRepository.delete(taskById);
    }

    @Transactional
    public TaskDTO updateToComplete(Long id) {
        Task taskById = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found a task with this id"));

        taskById.setTaskState(TaskState.COMPLETED);
        taskRepository.save(taskById);
        Task updatedTask = taskRepository.findById(taskById.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to update task state"));
        return TaskDTO.mapToTaskDTO(updatedTask);
    }

    @Transactional
    public void deleteAllCompleted() {
        taskRepository.deleteAllByCompleted(TaskState.COMPLETED);
    }
}
