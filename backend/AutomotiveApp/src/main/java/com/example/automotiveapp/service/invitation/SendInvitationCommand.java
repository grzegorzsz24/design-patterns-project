package com.example.automotiveapp.service.invitation;

import com.example.automotiveapp.service.Command;
import lombok.RequiredArgsConstructor;

// L3 Command - first impl
@RequiredArgsConstructor
public class SendInvitationCommand implements Command {

    private final InvitationService invitationService;
    private final Long receiverId;

    @Override
    public void execute() {
        invitationService.sendInvitation(receiverId);
    }
}
