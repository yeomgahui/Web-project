package com.cartrapido.main.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("select p From ChatMessage p where p.roomId =:roomId order by p.id ASC")
    List<ChatMessage> findByRoomId(String roomId);
}
