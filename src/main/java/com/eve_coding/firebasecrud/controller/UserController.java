package com.eve_coding.firebasecrud.controller;

import com.eve_coding.firebasecrud.model.User;
import com.eve_coding.firebasecrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<Object> saveUser(@RequestBody User user) throws ExecutionException, InterruptedException {
       String response = userService.saveUser(user);
        return new ResponseEntity<>("user added: " + response, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> users() throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(userService.users(), HttpStatus.OK);
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<Object> user(@PathVariable("name") String name) throws ExecutionException, InterruptedException {
        User user = userService.user(name);
        if(user != null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("not found",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{name}")
    public ResponseEntity<Object> deleteUser(@PathVariable("name") String name) throws ExecutionException, InterruptedException {
        String response = userService.deleteUser(name);
        if (response.equals("user not found")){
            return new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("user deleted on: "+response,HttpStatus.OK);
        }
    }
}
