package com.example.demo.controllers;

import com.example.demo.dao.UserRepository;
import com.example.demo.dto.MessageDto;
import com.example.demo.dto.MessageResponseDto;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/message")
@RestController
public class MessageRestController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    @Autowired
    public MessageRestController(MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MessageResponseDto> create(@RequestBody MessageDto message) {
        List<MessageDto> messageDtos = new ArrayList<>();
        messageDtos.add(messageService.createMessage(message));
        MessageResponseDto messageResponseDto = new MessageResponseDto();

        messageResponseDto.setStatusCode(CREATED.value());
        messageResponseDto.setContent(messageDtos);

        return new ResponseEntity<>(messageResponseDto, CREATED);
    }



    @RequestMapping( method = RequestMethod.PUT)
    public ResponseEntity<MessageResponseDto> update(@RequestBody MessageDto message) {
        messageService.update(message);
        List<MessageDto> messageDtos = new ArrayList<>();
        messageDtos.add(message);
        MessageResponseDto messageResponseDto = new MessageResponseDto();

        messageResponseDto.setStatusCode(OK.value());
        messageResponseDto.setContent(messageDtos);

        return new ResponseEntity<>(messageResponseDto, OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<MessageResponseDto> getAll() {
        List<MessageDto> messageDtos = messageService.getAll();
        MessageResponseDto messageResponseDto = new MessageResponseDto();

        messageResponseDto.setStatusCode(OK.value());
        messageResponseDto.setContent(messageDtos);

        return new ResponseEntity<>(messageResponseDto, OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MessageResponseDto> getById(@PathVariable("id") UUID id) {
        List<MessageDto> messageDtos = new ArrayList<>();
        messageDtos.add(messageService.getById(id));

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setStatusCode(OK.value());
        messageResponseDto.setContent(messageDtos);

        return new ResponseEntity<>(messageResponseDto, OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<MessageResponseDto> removeById(@PathVariable("id") UUID id) {
        messageService.removeById(id);

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setStatusCode(OK.value());
        messageResponseDto.setContent(null);

        return new ResponseEntity<>(messageResponseDto, OK);
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ResponseEntity<MessageResponseDto> getAllMyAssignedMessages(@RequestBody UUID id){

        userRepository.findById(id).orElseThrow();

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setStatusCode(OK.value());
        messageResponseDto.setContent(messageService.getMyAssignedMessages(id));

        return new ResponseEntity<>(messageResponseDto, OK);
    }

    @RequestMapping(value = "/my/reported", method = RequestMethod.GET)
    public ResponseEntity<MessageResponseDto> getAllMyReportedMessages(@RequestBody UUID id){

        userRepository.findById(id).orElseThrow();

        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setStatusCode(OK.value());
        messageResponseDto.setContent(messageService.getMyReportedMessages(id));

        return new ResponseEntity<>(messageResponseDto, OK);
    }

    /*@RequestMapping(value = "/my/discussions", method = RequestMethod.GET)
    public ResponseEntity<DiscussionResponseDto> getMyDiscussion(@RequestBody UUID id){


        DiscussionResponseDto dto = new DiscussionResponseDto();
        dto.setStatusCode(OK.value());
        dto.setContent(messageService.createDiscusion(id));

        return new ResponseEntity<>(dto, OK);
    }*/

}
