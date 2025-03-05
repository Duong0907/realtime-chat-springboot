package com.example.demo.repository;

import com.example.demo.entity.LastRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LastReadRepository extends JpaRepository<LastRead, Long> {
    @Query("SELECT lr FROM LastRead lr JOIN lr.message m WHERE m.conversation.id = :conversationId")
    List<LastRead> findLastReadsByConversationId(@Param("conversationId") Long conversationId);
}
