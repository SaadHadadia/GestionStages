package com.example.GestionStages.repositories;

import com.example.GestionStages.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);
}
