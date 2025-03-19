package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.repository.ChannelRepository;
import com.example.automotiveapp.repository.FriendshipRepository;
import com.example.automotiveapp.repository.InvitationRepository;

public interface InvitationState {

    void accept(Invitation invitation,
                InvitationRepository invitationRepository,
                FriendshipRepository friendshipRepository,
                ChannelRepository channelRepository);

    void reject(Invitation invitation,
                InvitationRepository invitationRepository);
}
