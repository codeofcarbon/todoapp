package com.codeofcarbon.todoapp.repositories;

import com.codeofcarbon.todoapp.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface TaskRepository  extends JpaRepository<Task, Long> {

    @Modifying
    @Query(value = "DELETE FROM tasks t WHERE t.taskState = :taskState")
    void deleteAllByCompleted(@Param("taskState") TaskState taskState);
}
