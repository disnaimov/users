/*package com.example.demo.controllers;

import com.example.demo.entities.Message;
import com.example.demo.dao.MessageRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/message")
@RestController
public class MessageRestController {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageRestController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public List<Message> getAll(){
        return messageRepository.findAll();
    }

    @GetMapping("/{id}")
    public Message getById(@PathVariable("id") UUID id){
        return messageRepository.findById(id).get();
    }

    @PutMapping
    public Message update(@RequestBody Message message){
        if (messageRepository.existsById(message.getId())){
            return messageRepository.save(message);
        }
        throw new EntityExistsException("Message with id:" + message.getId() + " doesn't not exist");
    }

    @PostMapping
    public Message create(@RequestBody Message message){
        UUID id = message.getId();
        if (id != null){
            if (messageRepository.existsById(message.getId())){
                throw new EntityExistsException("Message already exist");
            }
        }
        return messageRepository.save(message);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id){
        messageRepository.deleteById(id);
    }



}*/
