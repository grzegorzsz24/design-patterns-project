package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.domain.InvitationStatus;
import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    @Transient
    private InvitationState state;

    public InvitationState getState() {
        return switch (status) {
            case PENDING -> new PendingInvitationState();
            case ACCEPTED -> new AcceptedInvitationState();
            case REJECTED -> new RejectedInvitationState();
        };
    }
}
