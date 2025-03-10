package com.example.automotiveapp.domain.friendship;

import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_user_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "second_user_id")
    private User user2;
}
