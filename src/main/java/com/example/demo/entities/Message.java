package com.example.demo.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "tr_message")
@Entity
@Getter
@Setter
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;


    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;


    @Column(name = "create_date")
    private long created;

    @Column(name = "delete_date")
    private long deleted;

    @Column(name = "status")
    private String status;

}