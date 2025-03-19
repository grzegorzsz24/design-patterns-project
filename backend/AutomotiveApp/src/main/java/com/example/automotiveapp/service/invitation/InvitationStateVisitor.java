package com.example.automotiveapp.service.invitation;

import com.example.automotiveapp.domain.invitation.AcceptedInvitationState;
import com.example.automotiveapp.domain.invitation.Invitation;
import com.example.automotiveapp.domain.invitation.PendingInvitationState;
import com.example.automotiveapp.domain.invitation.RejectedInvitationState;

// L3 Visitor - third impl
public interface InvitationStateVisitor {
    void visit(PendingInvitationState state, Invitation invitation);
    void visit(AcceptedInvitationState state, Invitation invitation);

    void visit(RejectedInvitationState state, Invitation invitation);
}
