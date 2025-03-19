package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.exception.BadRequestException;
import com.example.automotiveapp.repository.ChannelRepository;
import com.example.automotiveapp.repository.FriendshipRepository;
import com.example.automotiveapp.repository.InvitationRepository;

public class RejectedInvitationState implements InvitationState {

    @Override
    public void accept(Invitation invitation, InvitationRepository invitationRepository, FriendshipRepository friendshipRepository, ChannelRepository channelRepository) {
        throw new BadRequestException("Zaproszenie jest odrzucone. Nie można go już zaakceptować.");

    }

    @Override
    public void reject(Invitation invitation, InvitationRepository invitationRepository) {
        throw new BadRequestException("Zaproszenie jest już odrzucone.");
    }
}
