package com.example.demo.service;


import com.example.demo.dao.MessageRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import com.example.demo.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;


    @Autowired
    public MessageService(MessageRepository messageRepository,
                          UserRepository userRepository,
                          ModelMapper mapper) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }


      public MessageDto createMessage(MessageDto messageDto) {
        log.info("Saving message");
        log.debug("Saving message {}", messageDto.toString());

        Optional<User> assignee = userRepository.findById(messageDto.getAssignee());
        Optional<User> reporter = userRepository.findById(messageDto.getReporter());

          Message message = new Message();
          message.setId(messageDto.getId());
          message.setDescription(messageDto.getDescription());
          message.setAssignee(assignee.get());
          message.setReporter(reporter.get());
          message.setCreated(messageDto.getCreated());
          message.setDeleted(messageDto.getDeleted());
          message.setStatus(messageDto.getStatus());

        UUID parentId = messageDto.getParent();

        if (messageRepository.findById(parentId).isPresent()) {
              Optional<Message> parent = messageRepository.findById(parentId);
              message.setParent(parent.get());
        }

        message = messageRepository.save(message);

        MessageDto saved = mapper.map(message, MessageDto.class);

        log.info("Message saved");
        log.debug("Message saved {}", saved.toString());
        return saved;
    }

    public MessageDto update(MessageDto messageDto) {
        log.info("updating message");
        log.debug("Updating message {}", messageDto.toString());
        Message message = messageRepository.findById(messageDto.getId()).orElseThrow();

        Optional<User> assignee = userRepository.findById(messageDto.getAssignee());
        Optional<User> reporter = userRepository.findById(messageDto.getReporter());

        message.setDescription(messageDto.getDescription());
        message.setAssignee(assignee.get());
        message.setReporter(reporter.get());
        message.setCreated(messageDto.getCreated());
        message.setDeleted(messageDto.getDeleted());
        message.setStatus(messageDto.getStatus());

        message = messageRepository.save(message);
        MessageDto updated =mapper.map(message, MessageDto.class);

        log.info("Message updated");
        log.debug("Message updated {}", updated.toString());
        return updated;
    }

    public void removeById(UUID id) {
        Optional<Message> message = messageRepository.findById(id);
        log.info("removal message by id");
        log.debug("removal message by id {}", id);

        if (!messageRepository.existsById(id)) {
            message.orElseThrow();
        }

        log.info("message removed");
        log.debug("message removed {}", message);
        messageRepository.deleteById(id);
    }

    public List<MessageDto> getAll() {
        log.info("getting all messages");
        log.debug("getting all messages");
        List<MessageDto> messageDtos = new ArrayList<>();
        List<Message> messages = messageRepository.findAll();
        MessageDto messageDto;

        for (Message m : messages) {
            messageDto = mapper.map(m, MessageDto.class);
            messageDtos.add(messageDto);
        }

        log.info("all messages received");
        log.debug("all messages received {}", messageDtos);
        return messageDtos;
    }

    public MessageDto getById(UUID id) {
        log.info("getting message by id");
        log.debug("getting message by id {}", id.toString());
        Optional<Message> message = messageRepository.findById(id);
        message.orElseThrow();

        log.info("message received");
        log.debug("message received {}", message);
        MessageDto messageDto = mapper.map(message.get(), MessageDto.class);

        return messageDto;
    }

    public List<MessageDto> getMyAssignedMessages(UUID id){

        log.info("getting all assigned messages by id");
        log.debug("getting assigned messages by id {}", id.toString());
        Optional<User> assignee = userRepository.findById(id);

        List<MessageDto> assignedMessageDtos = new ArrayList<>();
        List<Message> messages = messageRepository.findByAssignee(assignee);

        for (Message m: messages){
                assignedMessageDtos.add(mapper.map(m, MessageDto.class));
        }

        log.info("all messages received");
        log.debug("all messages received {}", assignedMessageDtos);
        return assignedMessageDtos;
    }

    public List<MessageDto> getMyReportedMessages(UUID id){

        log.info("getting all reported messages by id");
        log.debug("getting reported messages by id {}", id.toString());
        Optional<User> reporter = userRepository.findById(id);

        List<MessageDto> reportedMessageDtos = new ArrayList<>();
        List<Message> messages = messageRepository.findByReporter(reporter);

        for (Message m: messages) {
                reportedMessageDtos.add(mapper.map(m, MessageDto.class));
        }

        log.info("all messages received");
        log.debug("all messages received {}", reportedMessageDtos);
        return reportedMessageDtos;
    }
}
