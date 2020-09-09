package com.cartrapido.main.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    /*@Query("select p From ChatRoom p order by p.id DESC")
    List<ChatRoom> findAllDesc();*/
    List<ChatRoom> findByRoomId(String roomId);
    List<ChatRoom> findByRoomName(String roomName);

    List<ChatRoom> findAllByShopperId(String email);

    List<ChatRoom> findAllByClientId(String email);

}
