package com.example.demo.dto;

import com.example.demo.entities.User;
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
    private User assignee;
    private User reporter;
    private long created;
    private long deleted;
    private String status;
}
