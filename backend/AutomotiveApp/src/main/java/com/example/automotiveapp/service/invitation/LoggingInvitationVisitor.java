package com.example.automotiveapp.service.invitation;


import com.example.automotiveapp.domain.invitation.AcceptedInvitationState;
import com.example.automotiveapp.domain.invitation.Invitation;
import com.example.automotiveapp.domain.invitation.PendingInvitationState;
import com.example.automotiveapp.domain.invitation.RejectedInvitationState;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingInvitationVisitor implements InvitationStateVisitor {

    @Override
    public void visit(PendingInvitationState state, Invitation invitation) {
        log.info("Invitation {} in state PENDING. Sender: {}, Receiver: {}",
                invitation.getId(),
                invitation.getSender().getNickname(),
                invitation.getReceiver().getNickname());
    }

    @Override
    public void visit(AcceptedInvitationState state, Invitation invitation) {
        log.info("Invitation {} in state ACCEPTED. Sender: {}, Receiver: {}",
                invitation.getId(),
                invitation.getSender().getNickname(),
                invitation.getReceiver().getNickname());
    }

    @Override
    public void visit(RejectedInvitationState state, Invitation invitation) {
        log.info("Invitation {} in state REJECTED. Sender: {}, Receiver: {}",
                invitation.getId(),
                invitation.getSender().getNickname(),
                invitation.getReceiver().getNickname());
    }
}
