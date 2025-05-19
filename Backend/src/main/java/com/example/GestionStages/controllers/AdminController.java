package com.example.GestionStages.controllers;

import com.example.GestionStages.dto.*;
import com.example.GestionStages.models.*;
import com.example.GestionStages.services.*;
import com.example.GestionStages.mappers.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MapperService mapperService;

    @PostMapping
    public ResponseEntity<AdminDTO> createAdmin(@RequestBody AdminDTO adminDTO) {
        Admin admin = mapperService.toAdmin(adminDTO);
        Admin savedAdmin = adminService.addAdmin(admin);
        return ResponseEntity.ok(mapperService.toAdminDTO(savedAdmin));
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(mapperService.toAdminDTOList(admins));
    }

    @GetMapping("/{username}")
    public ResponseEntity<AdminDTO> getAdminByUsername(@PathVariable String username) {
        Optional<Admin> admin = adminService.getAdminByUsername(username);
        return admin.map(value -> ResponseEntity.ok(mapperService.toAdminDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{username}")
    public ResponseEntity<AdminDTO> updateAdmin(@PathVariable String username, @RequestBody AdminDTO adminDTO) {
        Optional<Admin> existingAdmin = adminService.getAdminByUsername(username);
        if (existingAdmin.isPresent()) {
            Admin admin = mapperService.toAdmin(adminDTO);
            admin.setUsername(username);
            Admin updatedAdmin = adminService.updateAdmin(admin);
            return ResponseEntity.ok(mapperService.toAdminDTO(updatedAdmin));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String username) {
        adminService.deleteAdmin(username);
        return ResponseEntity.noContent().build();
    }
}
