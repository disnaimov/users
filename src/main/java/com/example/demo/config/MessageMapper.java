package com.example.demo.config;

import com.example.demo.dao.MessageRepository;
import com.example.demo.dto.MessageDto;
import com.example.demo.entities.Message;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;


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

    @PostConstruct
    public void setupMapper(){
        modelMapper.createTypeMap(Message.class, MessageDto.class)
                .addMappings(mapping -> mapping.skip(MessageDto :: setAssignee)).setPostConverter(toDtoConverter());

        modelMapper.createTypeMap(Message.class, MessageDto.class)
                .addMappings(mapping -> mapping.skip(MessageDto :: setReporter)).setPostConverter(toDtoConverter());
    }


    public Converter<Message, MessageDto> toDtoConverter() {
        return context -> {
            Message source = context.getSource();
            MessageDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    /*public void mapSpecificFields(Message source, MessageDto destination){
        destination.setAssignee(Objects.isNull(source) || Objects.isNull(source.getId())
        ? null : source.getAssignee().getId());
        destination.setReporter(Objects.isNull(source) || Objects.isNull(source.getId())
        ? null : source.getReporter().getId());
    }*/

    void mapSpecificFields(Message source, MessageDto destination){
        destination.setAssignee(getAssigneeId(source));
        destination.setReporter(getReporterId(source));
    }

    private UUID getAssigneeId(Message source){
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getAssignee().getId();
    }

    private UUID getReporterId(Message source){
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getReporter().getId();
    }
}
