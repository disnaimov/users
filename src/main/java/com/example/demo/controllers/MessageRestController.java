package com.example.demo.controllers;

import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import com.example.demo.dao.MessageRepository;
import com.example.demo.service.MessageService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/message")
@RestController
public class MessageRestController {

    private final MessageService messageService;

    @Autowired
    public MessageRestController(MessageService messageService){
        this.messageService = messageService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public MessageDto create(@RequestBody MessageDto message){
        return messageService.create(message);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public MessageDto update(@RequestBody MessageDto message){
        return messageService.update(message);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MessageDto> getAll(){
        return messageService.getAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public MessageDto getById(@PathVariable("id") UUID id){
        return messageService.getById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeById(@PathVariable("id") UUID id){
        messageService.removeById(id);
    }
}
