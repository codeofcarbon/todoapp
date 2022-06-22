package com.codeofcarbon.todoapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity(name = "tasks")
@Getter
@Setter
//@NoArgsConstructor
@RequiredArgsConstructor
//@AllArgsConstructor
public class Task {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date = LocalDateTime.now();
    @NotBlank
//    @Length(min = 3, max = 40)
    private String name;
    @Enumerated(EnumType.STRING)
    private TaskState taskState = TaskState.ACTIVE;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @ManyToOne
    private User owner;

    public Task(LocalDateTime now, String nowy_task, TaskState active, Priority high, User user) {
        this.date = now;
        this.name = nowy_task;
        this.taskState = active;
        this.priority = high;
        this.owner = user;
    }
}
