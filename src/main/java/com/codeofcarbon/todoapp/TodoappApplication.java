package com.codeofcarbon.todoapp;

import com.codeofcarbon.todoapp.entities.Priority;
import com.codeofcarbon.todoapp.entities.Task;
import com.codeofcarbon.todoapp.entities.TaskState;
import com.codeofcarbon.todoapp.entities.User;
import com.codeofcarbon.todoapp.repositories.TaskRepository;
import com.codeofcarbon.todoapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
public class TodoappApplication implements CommandLineRunner {
    private final TaskRepository tas;
    private final UserRepository us;

    public static void main(String[] args) {
        SpringApplication.run(TodoappApplication.class, args);
    }

    @Override
    public void run(String... args) {
        var user = new User("nowy", "haslonowedlugiue");
        us.save(user);
        var task = new Task(LocalDateTime.now(), "nowy task", TaskState.ACTIVE, Priority.HIGH, user);
        tas.save(task);
    }
}