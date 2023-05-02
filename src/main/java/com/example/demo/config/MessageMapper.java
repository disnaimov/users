package com.example.demo.config;

import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;



@Component
public class MessageMapper {

    private final ModelMapper modelMapper;
    @Autowired
    public MessageMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public MessageDto messageToDto(Message message){
        return Objects.isNull(message) ? null : modelMapper.map(message, MessageDto.class);
    }
}
