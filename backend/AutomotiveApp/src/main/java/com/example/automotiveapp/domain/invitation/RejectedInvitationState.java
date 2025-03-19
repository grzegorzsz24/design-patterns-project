package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.exception.BadRequestException;
import com.example.automotiveapp.repository.InvitationRepositories;
import com.example.automotiveapp.repository.InvitationRepository;
import com.example.automotiveapp.service.invitation.InvitationStateVisitor;

public class RejectedInvitationState implements InvitationState {

    @Override
    public void accept(Invitation invitation, InvitationRepositories invitationRepositories) {
        throw new BadRequestException("Zaproszenie jest odrzucone. Nie można go już zaakceptować.");

    }

    @Override
    public void reject(Invitation invitation, InvitationRepository invitationRepository) {
        throw new BadRequestException("Zaproszenie jest już odrzucone.");
    }

    @Override
    public void acceptVisitor(InvitationStateVisitor visitor, Invitation invitation) {
        visitor.visit(this, invitation);
    }
}
