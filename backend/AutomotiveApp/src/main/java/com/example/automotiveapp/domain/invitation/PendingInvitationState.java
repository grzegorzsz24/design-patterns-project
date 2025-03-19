package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.domain.Channel;
import com.example.automotiveapp.domain.InvitationStatus;
import com.example.automotiveapp.domain.friendship.Friendship;
import com.example.automotiveapp.domain.friendship.FriendshipBuilder;
import com.example.automotiveapp.repository.InvitationRepositories;
import com.example.automotiveapp.repository.InvitationRepository;
import com.example.automotiveapp.service.invitation.InvitationStateVisitor;

public class PendingInvitationState implements InvitationState{

    @Override
    public void accept(Invitation invitation, InvitationRepositories invitationRepositories) {
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepositories.getInvitationRepository().save(invitation);

        Friendship friendship = new FriendshipBuilder()
                .user1(invitation.getSender())
                .user2(invitation.getReceiver())
                .build();
        invitationRepositories.getFriendshipRepository().save(friendship);

        Channel channel = new Channel();
        channel.setSender(invitation.getReceiver());
        channel.setReceiver(invitation.getSender());
        invitationRepositories.getChannelRepository().save(channel);

        invitation.setState(new AcceptedInvitationState());
    }

    @Override
    public void reject(Invitation invitation, InvitationRepository invitationRepository) {
        invitation.setStatus(InvitationStatus.REJECTED);
        invitationRepository.save(invitation);
        invitation.setState(new RejectedInvitationState());
    }

    @Override
    public void acceptVisitor(InvitationStateVisitor visitor, Invitation invitation) {
        visitor.visit(this, invitation);
    }
}
