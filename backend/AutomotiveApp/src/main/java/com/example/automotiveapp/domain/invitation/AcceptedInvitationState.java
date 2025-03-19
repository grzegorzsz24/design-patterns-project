package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.exception.BadRequestException;
import com.example.automotiveapp.repository.ChannelRepository;
import com.example.automotiveapp.repository.FriendshipRepository;
import com.example.automotiveapp.repository.InvitationRepository;
import com.example.automotiveapp.service.invitation.InvitationStateVisitor;

public class AcceptedInvitationState implements InvitationState {

    @Override
    public void accept(Invitation invitation, InvitationRepository invitationRepository, FriendshipRepository friendshipRepository, ChannelRepository channelRepository) {
        throw new BadRequestException("Zaproszenie jest już zaakceptowane.");
    }

    @Override
    public void reject(Invitation invitation, InvitationRepository invitationRepository) {
        throw new BadRequestException("Nie można odrzucić zaproszenia, które jest już zaakceptowane.");
    }

    @Override
    public void acceptVisitor(InvitationStateVisitor visitor, Invitation invitation) {
        visitor.visit(this, invitation);
    }
}
