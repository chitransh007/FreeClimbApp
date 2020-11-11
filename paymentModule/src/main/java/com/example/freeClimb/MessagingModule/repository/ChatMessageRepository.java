package com.example.freeClimb.MessagingModule.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.freeClimb.MessagingModule.model.ChatMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

}
