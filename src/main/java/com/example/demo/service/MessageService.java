package com.example.demo.service;

import com.example.demo.dao.MessageRepository;
import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import jakarta.persistence.EntityExistsException;
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
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ModelMapper modelMapper;

    private MessageDto convertToMessageDto(Message message) {
        return modelMapper.map(message, MessageDto.class);
    }

    public MessageDto create(MessageDto messageDto){
        log.info("Saving message");
        log.debug("Saving message {}", messageDto.toString());
        Message message = modelMapper.map(messageDto, Message.class);
        message = messageRepository.save(message);
        MessageDto saved = convertToMessageDto(message);

        log.info("Message saved");
        log.debug("Message saved {}", saved.toString());
        return saved;
    }

    public MessageDto update(MessageDto messageDto){
        log.info("updating message");
        log.debug("Updating message {}", messageDto.toString());
        Message message = messageRepository.findById(messageDto.getId()).orElse(new Message());

        message.setDescription(messageDto.getDescription());
        message.setAssignee(messageDto.getAssignee());
        message.setReporter(messageDto.getReporter());
        message.setCreated(messageDto.getCreated());
        message.setDeleted(messageDto.getDeleted());
        message.setStatus(message.getStatus());

        message = messageRepository.save(message);
        MessageDto updated = convertToMessageDto(message);

        log.info("Message updated");
        log.debug("Message updated {}", updated.toString());
        return updated;
    }

    public void removeById(UUID id){
        if (!messageRepository.existsById(id)) {
            throw new EntityExistsException("Message with id:'" + id + "' doesn't exists");
        }
        messageRepository.deleteById(id);
    }

    public List<MessageDto> getAll(){
        log.info("getting all messages");
        log.debug("getting all messages");
        List<MessageDto> messageDtos = new ArrayList<>();
        List<Message> messages = messageRepository.findAll();

        for (Message m: messages) {
            messageDtos.add(convertToMessageDto(m));
        }

        log.info("all messages received");
        log.debug("all messages received {}", messageDtos);
        return messageDtos;
    }

    public MessageDto getById(UUID id){
        log.info("getting message by id");
        log.debug("getting message by id {}", id.toString());
        Optional<Message> message = messageRepository.findById(id);

        log.info("message received");
        log.debug("message received {}");
        return convertToMessageDto(message.get());
    }
}
