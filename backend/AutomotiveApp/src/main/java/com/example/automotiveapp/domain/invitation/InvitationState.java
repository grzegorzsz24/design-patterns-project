package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.repository.ChannelRepository;
import com.example.automotiveapp.repository.FriendshipRepository;
import com.example.automotiveapp.repository.InvitationRepository;
import com.example.automotiveapp.service.invitation.InvitationStateVisitor;

public interface InvitationState {

    void accept(Invitation invitation,
                InvitationRepository invitationRepository,
                FriendshipRepository friendshipRepository,
                ChannelRepository channelRepository);

    void reject(Invitation invitation,
                InvitationRepository invitationRepository);

    void acceptVisitor(InvitationStateVisitor visitor, Invitation invitation);
}
