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
//@Transactional
public class UserService {

    //error code: USER_NOT_FOUND
    //content: null
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto create(UserDto userDto) {
        log.info("Saving user");
        log.debug("Saving user {}", userDto.toString());
        User user = modelMapper.map(userDto, User.class);
        user = userRepository.save(user);
        UserDto saved = convertToUserInfo(user);

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
        UserDto updated = convertToUserInfo(user);

        log.info("User updated");
        log.debug("User updated {}", updated.toString());
        return updated;
    }


    public void removeById(UUID id) {
        Optional<User> user = userRepository.findById(id);

        if (!userRepository.existsById(id)) {
            user.orElseThrow(/*UserNotFoundException :: new*/);
        }

        userRepository.deleteById(id);
    }

    public List<UserDto> getAll() {
        log.info("getting all users");
        log.debug("getting all users");
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User u: users) {
           userDtos.add(convertToUserInfo(u));
        }

        log.info("all users received");
        log.debug("all users received {}", userDtos);
        return userDtos;
    }

    private UserDto convertToUserInfo(User user) {
        return modelMapper.map(user, UserDto.class);
    }


    public UserDto getById(UUID id){

        log.info("getting user by id");
        log.debug("getting user by id {}", id.toString());
        Optional<User> user = userRepository.findById(id);
        user.orElseThrow(/*UserNotFoundException:: new*/);

        log.info("user received");
        log.debug("user received {}", user);
        return convertToUserInfo(user.get());
    }
}
