package com.example.automotiveapp.domain;

import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String city;
    @Column(name = "event_date", columnDefinition = "DATETIME")
    private LocalDateTime eventDate;
    private String description;

    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
    private File image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
