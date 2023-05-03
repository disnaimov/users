package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    public UserDto create(UserDto userDto) {
        log.info("Saving user");
        log.debug("Saving user {}", userDto.toString());
        User user = modelMapper.map(userDto, User.class);
        user = userRepository.save(user);
        UserDto saved = convertToUserDto(user);

        log.info("User saved");
        log.debug("User saved {}", saved.toString());
        return saved;
    }


    public UserDto update(UserDto userDto) {
        log.info("updating user");
        log.debug("Updating user {}", userDto.toString());
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(/*UserNotFoundException :: new*/);

        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPassword(userDto.getPassword());

        user = userRepository.save(user);
        UserDto updated = convertToUserDto(user);

        log.info("User updated");
        log.debug("User updated {}", updated.toString());
        return updated;
    }


    public void removeById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        log.info("removal user by id");
        log.debug("removal user by id {}", id);

        if (!userRepository.existsById(id)) {
            user.orElseThrow();
        }

        log.info("user removed");
        log.debug("user removed {}", user);
        userRepository.deleteById(id);
    }

    public List<UserDto> getAll() {
        log.info("getting all users");
        log.debug("getting all users");
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User u: users) {
           userDtos.add(convertToUserDto(u));
        }

        log.info("all users received");
        log.debug("all users received {}", userDtos);
        return userDtos;
    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }


    public UserDto getById(UUID id){

        log.info("getting user by id");
        log.debug("getting user by id {}", id.toString());
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(/*UserNotFoundException:: new*/);

        log.info("user received");
        log.debug("user received {}", user);
        return convertToUserDto(user.get());
    }
}
