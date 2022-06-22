package com.codeofcarbon.todoapp.repositories;

import com.codeofcarbon.todoapp.entities.User;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    @Query("select u from users u where upper(u.userLogin) = upper(?1)")
    Optional<User> findByUserLoginIgnoreCase(String email);

    @Query("select (count(u) > 0) from users u where upper(u.userLogin) = upper(?1)")
    Boolean existsUserByUserLoginIgnoreCase(String email);
}
