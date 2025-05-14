package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.UserDTO;
import com.example.GestionStages.models.Admin;
import com.example.GestionStages.models.Stagiaire;
import com.example.GestionStages.models.Tuteur;
import com.example.GestionStages.models.User;
import com.example.GestionStages.mappers.MapperService;
import com.example.GestionStages.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final MapperService mapperService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserController(UserService userService, MapperService mapperService) {
        this.userService = userService;
        this.mapperService = mapperService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> {
                    UserDTO dto = mapperService.toUserDTO(user);
                    if (user instanceof Admin) {
                        dto.setType("Admin");
                    }
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> userOpt = userService.getUserByUsername(username);
        return userOpt.map(user -> ResponseEntity.ok(mapperService.toUserDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/addTuteur")
    public User addTuteur(@RequestBody Tuteur tuteur){
        tuteur.setPassword(encoder.encode("1234"));
        return userService.addUser(tuteur);
    }

    @PostMapping("/addStagiaire")
    public User addStagiaire(@RequestBody Stagiaire stagiaire){
        stagiaire.setPassword(encoder.encode("1234"));
        return userService.addUser(stagiaire);
    }

    @PostMapping("/addAdmin")
    public User addAdmin(@RequestBody Admin admin){
        admin.setPassword(encoder.encode("1234"));
        return userService.addUser(admin);
    }
}
