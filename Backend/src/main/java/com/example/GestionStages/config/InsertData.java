package com.example.GestionStages.config;

import com.example.GestionStages.models.Admin;
import com.example.GestionStages.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InsertData {
    
    private static final Logger logger = LoggerFactory.getLogger(InsertData.class);
    
    @Autowired
    private Environment env;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Bean
    public boolean createRootAdmin() {
        String username = env.getProperty("root.admin.username");
        String password = env.getProperty("root.admin.password");

        if (username == null || password == null) {
            logger.error("Root admin credentials are not set in the environment variables.");
            throw new IllegalStateException("Root admin credentials are not set in the environment variables.");
        }

        // Check if the root admin already exists
        if (userRepository.findByUsername(username).isPresent()) {
            logger.info("Root admin already exists.");
            return true;
        }

        // Create the root admin
        Admin admin = new Admin(
                username,
                encoder.encode(password),
                "",
                ""
        );

        try {
            userRepository.save(admin);
            logger.info("Root admin created successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to create root admin: {}", e.getMessage());
            throw new IllegalStateException("Application startup failed due to root admin creation error.", e);
        }
    }
}
