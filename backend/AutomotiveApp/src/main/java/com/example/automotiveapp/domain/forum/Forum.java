package com.example.automotiveapp.domain.forum;

import com.example.automotiveapp.domain.Car;
import com.example.automotiveapp.domain.Comment;
import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "forum")
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int commentsNumber;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.REMOVE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
