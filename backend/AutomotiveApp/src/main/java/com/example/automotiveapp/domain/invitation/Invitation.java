package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.domain.InvitationStatus;
import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.*;
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
        if (this.status == InvitationStatus.PENDING) {
            return new PendingInvitationState();
        } else if (this.status == InvitationStatus.ACCEPTED) {
            return new AcceptedInvitationState();
        } else {
            return new RejectedInvitationState();
        }
    }
}
