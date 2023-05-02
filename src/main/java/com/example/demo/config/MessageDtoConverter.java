/*package com.example.demo.config;

import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NamingConventions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@Configuration
public class MessageDtoConverter {


    @Autowired
    private ModelMapper modelMapper;





    *//*public void MessageToDtoConverter(){

        modelMapper.createTypeMap(Message.class, MessageDto.class)
                .addMapping(Message :: getAssignee, MessageDto :: setAssignee)
                .addMapping(Message :: getReporter, MessageDto :: setReporter);
    }*//*




    public MessageDto ConvertMessageToDto(Message message){
        modelMapper.createTypeMap(Message.class, MessageDto.class)
                .addMapping(Message :: getId, MessageDto :: setId)
                .addMapping(Message ::getDescription, MessageDto :: setDescription)
                .addMapping(Message :: getAssignee, MessageDto :: setAssignee)
                .addMapping(Message :: getReporter, MessageDto :: setReporter)
                .addMapping(Message :: getCreated, MessageDto :: setCreated)
                .addMapping(Message :: getDeleted, MessageDto :: setDeleted)
                .addMapping(Message :: getStatus, MessageDto :: setStatus);


        return modelMapper.map(message, MessageDto.class);
    }
}*/
