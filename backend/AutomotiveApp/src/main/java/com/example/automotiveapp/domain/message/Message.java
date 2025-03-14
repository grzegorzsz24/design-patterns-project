package com.example.automotiveapp.domain.message;

import com.example.automotiveapp.domain.Channel;
import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
    private String message;
    private LocalDateTime createdAt;
}