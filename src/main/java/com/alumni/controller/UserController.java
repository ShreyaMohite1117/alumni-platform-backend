package com.alumni.controller;

import com.alumni.entity.User;
import com.alumni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getCurrentUser(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateProfile(@RequestBody User updatedUser, Authentication authentication) {
        return ResponseEntity.ok(userService.updateProfile(authentication.getName(), updatedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/alumni")
    public ResponseEntity<List<User>> getAllAlumni() {
        return ResponseEntity.ok(userService.getAllAlumni());
    }

    @GetMapping("/alumni/search")
    public ResponseEntity<List<User>> searchAlumni(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchAlumni(keyword));
    }

    @GetMapping("/alumni/company")
    public ResponseEntity<List<User>> getAlumniByCompany(@RequestParam String company) {
        return ResponseEntity.ok(userService.findAlumniByCompany(company));
    }

    @GetMapping("/alumni/domain")
    public ResponseEntity<List<User>> getAlumniByDomain(@RequestParam String domain) {
        return ResponseEntity.ok(userService.findAlumniByDomain(domain));
    }
}
