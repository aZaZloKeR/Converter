package com.example.converter.db.repositories;

import com.example.converter.db.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepo extends CrudRepository<User,Integer> {
    Optional<User> findByLogin(String login);
}
