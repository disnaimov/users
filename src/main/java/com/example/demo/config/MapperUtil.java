package com.example.demo.config;

import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class MapperUtil {
    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper();
    }

    private ModelMapper modelMapper;

    public Converter<Message, MessageDto> messageToDtoConverter(){
        return context -> {
            Message source = context.getSource();
            MessageDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    public MessageDto messageToDto(Message message) {
        return Objects.isNull(message) ? null : modelMapper.map(message, MessageDto.class);
    }

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Message.class, MessageDto.class)
                .addMappings(mapping -> mapping.skip(MessageDto :: setAssignee))
                .setPostConverter(messageToDtoConverter())
                .addMappings(mapping -> mapping.skip(MessageDto :: setReporter))
                .setPostConverter(messageToDtoConverter());
    }


    public void mapSpecificFields(Message source, MessageDto destination){
        destination.setAssignee(Objects.isNull(source) || Objects.isNull(source.getId()) ? null
                : source.getAssignee().getId());
        destination.setReporter(Objects.isNull(source) || Objects.isNull(source.getId())
        ? null : source.getReporter().getId());
    }



    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toList());
    }
}
