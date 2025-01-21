package ru.fast.bills.web.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.fast.bills.services.UserService;
import ru.fast.bills.web.dto.User;

import java.util.List;
import java.util.UUID;

import static ru.fast.bills.utils.AuthorityConstant.SUPER_ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> newUser(@RequestBody @Valid User user) {
        User newUser = this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping
    @PreAuthorize(SUPER_ADMIN)
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PutMapping(path = "{userId}")
    public ResponseEntity<User> updateUser(@PathVariable("userId") UUID userId,
                                           @RequestBody @Valid User newUser) {

        User user = this.userService.updateUser(userId, newUser);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") UUID userId) {
        this.userService.deleteUser(userId);
    }


}
