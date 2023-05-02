package com.example.demo.service;

//import com.example.demo.config.MessageDtoConverter;
import com.example.demo.config.MapperUtil;
import com.example.demo.config.MessageMapper;
import com.example.demo.dao.MessageRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.MessageDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.Message;
import com.example.demo.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
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
    private final MapperUtil modelMapper;

    private final MessageMapper mapper;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository, MapperUtil modelMapper,MessageMapper mapper){
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.mapper = mapper;
    }



    /*@Autowired
    private MessageDtoConverter messageDtoConverter;*/

    /*private MessageDto convertToMessageDto(Message message) {
        return modelMapper.map(message, MessageDto.class);
    }*/

    private MessageDto convertMessageToDto(Message message){
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setDescription(message.getDescription());
        messageDto.setAssignee(message.getAssignee().getId());
        messageDto.setReporter(message.getReporter().getId());
        messageDto.setCreated(message.getCreated());
        messageDto.setDeleted(message.getDeleted());
        messageDto.setStatus(message.getStatus());

        return messageDto;
    }

    public MessageDto create(MessageDto messageDto){
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
        MessageDto saved = mapper.messageToDto(message);

        log.info("Message saved");
        log.debug("Message saved {}", saved.toString());
            return saved;
    }



    public MessageDto update(MessageDto messageDto){
        log.info("updating message");
        log.debug("Updating message {}", messageDto.toString());
        Message message = new Message();

        Optional<User> assignee = userRepository.findById(messageDto.getAssignee());
        Optional<User> reporter = userRepository.findById(messageDto.getReporter());

        message.setDescription(messageDto.getDescription());
        message.setAssignee(assignee.get());
        message.setReporter(reporter.get());
        message.setCreated(messageDto.getCreated());
        message.setDeleted(messageDto.getDeleted());
        message.setStatus(messageDto.getStatus());

        message = messageRepository.save(message);
        MessageDto updated = convertMessageToDto(message);

        log.info("Message updated");
        log.debug("Message updated {}", updated.toString());
        return updated;
    }

    public void removeById(UUID id){
        Optional<Message> message = messageRepository.findById(id);

        if (!messageRepository.existsById(id)){
            message.orElseThrow();
        }

        messageRepository.deleteById(id);
    }

    public List<MessageDto> getAll(){
        log.info("getting all messages");
        log.debug("getting all messages");
        List<MessageDto> messageDtos = new ArrayList<>();
        List<Message> messages = messageRepository.findAll();

        for (Message m:messages) {
            messageDtos.add(convertMessageToDto(m));
        }

        log.info("all messages received");
        log.debug("all messages received {}", messageDtos);
        return messageDtos;
    }

    public MessageDto getById(UUID id){
        log.info("getting message by id");
        log.debug("getting message by id {}", id.toString());
        Optional<Message> message = messageRepository.findById(id);
        message.orElseThrow();

        log.info("message received");
        log.debug("message received {}", message);
        return convertMessageToDto(message.get());
    }
}
