package com.example.demo.config;

import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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


    /*public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toList());
    }*/
}
