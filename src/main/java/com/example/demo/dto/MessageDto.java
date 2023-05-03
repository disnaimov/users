package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {
    private UUID id;
    private String description;
    private UUID assignee;
    private UUID reporter;
    private long created;
    private long deleted;
    private String status;

    /*public MessageDto(UserDto assignee, UserDto reporter){
        this.assignee = Integer.parseInt(assignee);
    }*/
}
