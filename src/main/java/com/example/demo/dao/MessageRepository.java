package com.example.demo.dao;

import com.example.demo.entities.Message;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    List<Message> findByAssignee(Optional<User> assignee);

    List<Message> findByReporter(Optional<User> reporter);
}
