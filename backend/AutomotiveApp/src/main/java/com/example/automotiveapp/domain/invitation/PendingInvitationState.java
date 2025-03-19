package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.domain.Channel;
import com.example.automotiveapp.domain.InvitationStatus;
import com.example.automotiveapp.domain.friendship.Friendship;
import com.example.automotiveapp.domain.friendship.FriendshipBuilder;
import com.example.automotiveapp.repository.ChannelRepository;
import com.example.automotiveapp.repository.FriendshipRepository;
import com.example.automotiveapp.repository.InvitationRepository;

public class PendingInvitationState implements InvitationState{

    @Override
    public void accept(Invitation invitation, InvitationRepository invitationRepository, FriendshipRepository friendshipRepository, ChannelRepository channelRepository) {
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);

        Friendship friendship = new FriendshipBuilder()
                .user1(invitation.getSender())
                .user2(invitation.getReceiver())
                .build();
        friendshipRepository.save(friendship);

        Channel channel = new Channel();
        channel.setSender(invitation.getReceiver());
        channel.setReceiver(invitation.getSender());
        channelRepository.save(channel);

        invitation.setState(new AcceptedInvitationState());
    }

    @Override
    public void reject(Invitation invitation, InvitationRepository invitationRepository) {
        invitation.setStatus(InvitationStatus.REJECTED);
        invitationRepository.save(invitation);
        invitation.setState(new RejectedInvitationState());
    }
}
