package Devmaster_Lesson4.controller;

import Devmaster_Lesson4.dto.UsersDTO;
import Devmaster_Lesson4.entity.User;
import Devmaster_Lesson4.service.UsersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class UserController {
    @Autowired
    UsersService usersService;
    @GetMapping("/user-list")
    public List<User>getAllUsers()
    {
        return usersService.findAll();
    }
    @PostMapping("/user-add")
    public ResponseEntity<String>addUser(@Valid @RequestBody UsersDTO user)
    {
        usersService.create(user);
        return ResponseEntity.badRequest().body("Users created successfully");
    }
}
