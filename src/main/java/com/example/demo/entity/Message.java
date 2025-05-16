package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant updatedAt;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "message")
    private Image image;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    private Conversation conversation;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<LastRead> lastReads = new ArrayList<>();
}
