package com.codeofcarbon.todoapp;

import com.codeofcarbon.todoapp.entities.*;
import com.codeofcarbon.todoapp.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.*;
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