package com.example.demo.service;

import ch.qos.logback.core.model.ModelUtil;
import com.example.demo.dto.UserInfo;
import com.example.demo.entities.User;
import com.example.demo.dao.UserRepository;
import jakarta.persistence.EntityExistsException;
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
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserInfo create(UserInfo userInfo) {
        log.info("Saving user");
        log.debug("Saving user {}", userInfo.toString());
        User user = modelMapper.map(userInfo, User.class);
        user = userRepository.save(user);
        //UserInfo saved = modelMapper.map(user, UserInfo.class);
        UserInfo saved = convertToUserInfo(user);

        log.info("User saved");
        log.debug("User saved {}", saved.toString());
        return saved;
    }


    public UserInfo update(UserInfo userInfo) {
        log.info("updating user");
        log.debug("Updating user {}", userInfo.toString());
        User user = userRepository.findById(userInfo.getId()).orElse(new User());

        user.setFirstname(userInfo.getFirstname());
        user.setLastname(userInfo.getLastname());
        user.setPassword(userInfo.getPassword());

        user = userRepository.save(user);
        UserInfo updated = convertToUserInfo(user);

        log.info("User updated");
        log.debug("User updated {}", updated.toString());
        return updated;
    }


    public void removeById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityExistsException("User with id:'" + id + "' doesn't exists");
        }
        userRepository.deleteById(id);
    }


    public List<UserInfo> getAll() {
        log.info("getting all users");
        log.debug("getting all users");
        List<UserInfo> userInfos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User u: users) {
           userInfos.add(convertToUserInfo(u));
        }

        log.info("all users received");
        log.debug("all users received {}", userInfos);
        return userInfos;
    }

    private UserInfo convertToUserInfo(User user) {
        return modelMapper.map(user, UserInfo.class);
    }


    public UserInfo getById(UUID id) {
        log.info("getting user by id");
        log.debug("getting user by id {}", id.toString());
        Optional<User> user = userRepository.findById(id);

        log.info("user received");
        log.debug("user received {}");
        return convertToUserInfo(user.get());
    }
}
