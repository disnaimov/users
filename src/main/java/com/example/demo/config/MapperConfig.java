package com.example.demo.config;

import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getMapper(){
        ModelMapper mapper = new ModelMapper();
        TypeMap<Message, MessageDto> typemap = mapper.createTypeMap(Message.class, MessageDto.class);
        typemap.addMapping(s->s.getAssignee().getId(), MessageDto::setAssignee);
        typemap.addMapping(s->s.getReporter().getId(), MessageDto::setReporter);
        return mapper;
    }

}
