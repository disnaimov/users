package com.example.demo.controllers;

import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/users")
@RestController
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    //private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResponseDto> create(@RequestBody UserDto user) {
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userService.create(user));
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setStatusCode(HttpStatus.CREATED.value());
        userResponseDto.setContent(userDtos);
        return new ResponseEntity<>(userResponseDto, CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<UserResponseDto> update(@RequestBody UserDto user) {
        userService.update(user);
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(user);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setStatusCode(OK.value());
        userResponseDto.setContent(userDtos);

        return new ResponseEntity<>(userResponseDto, OK);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserResponseDto> getAll() {
        List<UserDto> userDtos = userService.getAll();
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setStatusCode(OK.value());
        userResponseDto.setContent(userDtos);
        return new ResponseEntity<>(userResponseDto, OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDto> getById(@PathVariable("id") UUID id) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setStatusCode(OK.value());
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userService.getById(id));
        userResponseDto.setContent(userDtos);

        return new ResponseEntity<>(userResponseDto, OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UserResponseDto> removeById(@PathVariable("id") UUID id) {
        userService.removeById(id);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setContent(null);
        userResponseDto.setStatusCode(OK.value());

        return new ResponseEntity<>(userResponseDto, OK);
    }
}
