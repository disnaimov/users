package com.example.demo.dto;

import com.example.demo.entities.Message;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ToString(exclude = {"password"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private UUID id;
    private String firstname;
    private String lastname;
    private String password;
}
