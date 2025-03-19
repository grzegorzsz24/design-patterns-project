package com.example.automotiveapp.domain.invitation;

import com.example.automotiveapp.repository.InvitationRepositories;
import com.example.automotiveapp.repository.InvitationRepository;
import com.example.automotiveapp.service.invitation.InvitationStateVisitor;

public interface InvitationState {

    void accept(Invitation invitation, InvitationRepositories invitationRepositories);

    void reject(Invitation invitation,
                InvitationRepository invitationRepository);

    void acceptVisitor(InvitationStateVisitor visitor, Invitation invitation);
}
