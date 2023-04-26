package com.example.demo.controllers;

import com.example.demo.dto.UserInfo;
import com.example.demo.entities.User;
import com.example.demo.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/users")
@RestController
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService){
        this.userService = userService;
    }

    //private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);
    @RequestMapping(method = RequestMethod.POST)
    public UserInfo create(@RequestBody UserInfo user){
        return userService.create(user);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public UserInfo update(@RequestBody UserInfo user){
        return userService.update(user);
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<UserInfo> getAll(){
        return userService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserInfo getById(@PathVariable ("id") UUID id){
        return userService.getById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeById(@PathVariable("id") UUID id){
        userService.removeById(id);
    }





}
