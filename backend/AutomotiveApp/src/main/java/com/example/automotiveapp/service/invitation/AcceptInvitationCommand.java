package com.example.automotiveapp.service.invitation;

import com.example.automotiveapp.service.Command;
import lombok.RequiredArgsConstructor;

// L3 Command - second impl
@RequiredArgsConstructor
public class AcceptInvitationCommand implements Command {

    private final InvitationService invitationService;
    private final Long invitationId;

    @Override
    public void execute() {
        invitationService.acceptInvitation(invitationId);
    }
}
