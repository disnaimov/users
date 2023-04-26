package com.example.demo.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@ToString(exclude = {"password"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {
    private UUID id;
    private String firstname;
    private String lastname;
    private String password;
}
