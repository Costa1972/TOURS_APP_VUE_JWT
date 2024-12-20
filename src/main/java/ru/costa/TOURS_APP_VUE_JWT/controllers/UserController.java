package ru.costa.TOURS_APP_VUE_JWT.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.costa.TOURS_APP_VUE_JWT.models.User;
import ru.costa.TOURS_APP_VUE_JWT.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
//@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    private ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PostMapping("/")
    public void delete(@RequestBody User user) {
        userService.delete(user);
    }
}
