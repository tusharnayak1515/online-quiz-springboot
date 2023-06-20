package com.onlinequizapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlinequizapp.entities.User;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    public User findByEmail(String email);
}
