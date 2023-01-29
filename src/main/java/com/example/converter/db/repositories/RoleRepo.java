package com.example.converter.db.repositories;

import com.example.converter.db.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepo extends CrudRepository<Role,Integer> {
    Optional<Role> findByName(String name);
}
