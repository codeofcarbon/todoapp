package com.codeofcarbon.todoapp;

import com.codeofcarbon.todoapp.controllers.TaskController;
import com.codeofcarbon.todoapp.data.TestsData;
import com.codeofcarbon.todoapp.entities.*;
import com.codeofcarbon.todoapp.entities.dto.TaskDTO;
import com.codeofcarbon.todoapp.services.TaskService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService service;
    private TaskController controller;

    @BeforeEach
    void setUp() {
        controller = new TaskController(service);
    }

    @Test
    public void thatAddTaskWorksCorrectly() {
        final User OWNER = TestsData.generateSampleUser();
        final String NAME = "Write tests";
        final LocalDateTime DATE = LocalDateTime.of(2022, 6, 23, 23, 0);
        final Task valid = new Task(10L, DATE, NAME, TaskState.ACTIVE, Priority.HIGH ,OWNER);
        final TaskDTO validDTO = TaskDTO.mapToTaskDTO(valid);

        when(service.addTask(any())).thenReturn(validDTO);

        final ResponseEntity<TaskDTO> actual = controller.addTask(valid);
        final TaskDTO respJson = actual.getBody();

        assertThat(respJson).hasNoNullFieldsOrProperties();
        assertThat(respJson).isNotNull();
        assertThat(respJson.getId()).isEqualTo(validDTO.getId());
        assertThat(respJson.getDate()).isEqualTo(validDTO.getDate());
        assertThat(respJson.getName()).isEqualTo(validDTO.getName());
        assertThat(respJson.getState()).isEqualTo(validDTO.getState());
        assertThat(respJson.getPriority()).isEqualTo(validDTO.getPriority());
        assertThat(respJson.getOwner()).isEqualTo(validDTO.getOwner());
    }

    @Test
    public void thatShowTasksWorksCorrectly() {
        final List<Task> tasks = new ArrayList<>();
        final Task TASK = TestsData.generateSampleTask();
        final Task TASK2 = TestsData.generateSecondSampleTask();
        tasks.add(TASK);
        tasks.add(TASK2);
        final List<TaskDTO> taskDTOs = tasks.stream().map(TaskDTO::mapToTaskDTO).toList();
        when(service.getAllTasks()).thenReturn(taskDTOs);

        final ResponseEntity<List<TaskDTO>> actual = controller.showTasks();
        final List<TaskDTO> respJson = actual.getBody();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respJson).isNotNull();
        assertThat(respJson.size()).isEqualTo(2);
        assertThat(respJson.get(0).getDate()).isEqualTo(taskDTOs.get(0).getDate());
        assertThat(respJson.get(1).getDate()).isEqualTo(taskDTOs.get(1).getDate());
        assertThat(respJson.get(0).getName()).isEqualTo(taskDTOs.get(0).getName());
        assertThat(respJson.get(1).getName()).isEqualTo(taskDTOs.get(1).getName());
        assertThat(respJson.get(0).getState()).isEqualTo(taskDTOs.get(0).getState());
        assertThat(respJson.get(1).getState()).isEqualTo(taskDTOs.get(1).getState());
        assertThat(respJson.get(0).getPriority()).isEqualTo(taskDTOs.get(0).getPriority());
        assertThat(respJson.get(1).getPriority()).isEqualTo(taskDTOs.get(1).getPriority());
        assertThat(respJson.get(0).getOwner()).isEqualTo(taskDTOs.get(0).getOwner());
        assertThat(respJson.get(1).getOwner()).isEqualTo(taskDTOs.get(1).getOwner());
    }

    @Test
    public void thatRemoveTaskWorksCorrectly() {
        final ResponseEntity<HttpStatus> actual = controller.removeTask(2L);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actual.getBody()).isEqualTo(null);
    }

    @Test
    public void thatChangeToCompleteWorksCorrectly() {   // cheat

        // todo change return values from service methods from DTOs to Task
        Task TASK = TestsData.generateSampleTask();

        TASK.setTaskState(TaskState.COMPLETED);
        var taskDTO = TaskDTO.mapToTaskDTO(TASK);

        TASK.setTaskState(TaskState.ACTIVE);
        when(service.updateToComplete(1L)).thenReturn(taskDTO);

        final ResponseEntity<TaskDTO> actual = controller.changeToComplete(1L);
        final TaskDTO respJson = actual.getBody();

        System.err.println(TASK.toString());
        System.err.println(taskDTO);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respJson).isNotNull();
        assertThat(respJson.getDate()).isEqualTo(taskDTO.getDate());
        assertThat(respJson.getName()).isEqualTo(taskDTO.getName());
        assertThat(respJson.getState()).isEqualTo(taskDTO.getState());
        assertThat(respJson.getPriority()).isEqualTo(taskDTO.getPriority());
        assertThat(respJson.getOwner()).isEqualTo(taskDTO.getOwner());
    }

    @Test
    public void thatRemoveCompletedWorksCorrectly() {
        doNothing().when(service).deleteAllCompleted();

        ResponseEntity<Void> actual = controller.removeCompleted();

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
