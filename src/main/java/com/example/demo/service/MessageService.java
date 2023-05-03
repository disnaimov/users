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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    //private final MessageMapper mapper;
    private final ModelMapper mapper;


    @Autowired
    public MessageService(MessageRepository messageRepository,
                          UserRepository userRepository,
                          ModelMapper mapper) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public MessageDto create(MessageDto messageDto) {
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

        message = messageRepository.save(message);

        MessageDto saved = mapper.map(message, MessageDto.class);

        /*UUID assigneeId = assignee.get().getId();
        UUID reporterId = reporter.get().getId();

        saved.setAssignee(assigneeId);
        saved.setReporter(reporterId);*/

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
            UUID assignee = m.getAssignee().getId();
            UUID reporter = m.getReporter().getId();
            messageDto = mapper.map(m, MessageDto.class);
            messageDto.setAssignee(assignee);
            messageDto.setReporter(reporter);

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

        UUID assignee = message.get().getAssignee().getId();
        UUID reporter = message.get().getReporter().getId();

        log.info("message received");
        log.debug("message received {}", message);
        MessageDto messageDto = mapper.map(message.get(), MessageDto.class);
        messageDto.setAssignee(assignee);
        messageDto.setReporter(reporter);

        return messageDto;
    }
}
