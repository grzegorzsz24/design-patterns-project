package com.example.automotiveapp.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

// L6 3 parameters - dto
@Component
@Getter
@AllArgsConstructor
public class InvitationRepositories {
    private final InvitationRepository invitationRepository;
    private final FriendshipRepository friendshipRepository;
    private final ChannelRepository channelRepository;
}
