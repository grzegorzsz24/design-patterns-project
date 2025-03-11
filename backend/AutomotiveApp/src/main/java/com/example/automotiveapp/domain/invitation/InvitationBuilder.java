package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.domain.InvitationStatus;
import com.example.automotiveapp.domain.User.User;

// L1 Builder - second impl
public class InvitationBuilder {
    private Long id;
    private User sender;
    private User receiver;
    private InvitationStatus status;

    public InvitationBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public InvitationBuilder sender(User sender) {
        this.sender = sender;
        return this;
    }

    public InvitationBuilder receiver(User receiver) {
        this.receiver = receiver;
        return this;
    }

    public InvitationBuilder status(InvitationStatus status) {
        this.status = status;
        return this;
    }

    public Invitation build() {
        Invitation invitation = new Invitation();
        invitation.setId(id);
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setStatus(status);
        return invitation;
    }
}
