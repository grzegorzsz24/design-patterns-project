package com.example.automotiveapp.service.invitation;

import com.example.automotiveapp.domain.InvitationStatus;
import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.domain.invitation.Invitation;
import com.example.automotiveapp.domain.invitation.InvitationBuilder;
import com.example.automotiveapp.dto.InvitationDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.InvitationDtoMapper;
import com.example.automotiveapp.repository.InvitationRepositories;
import com.example.automotiveapp.repository.InvitationRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvitationService {
    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final InvitationRepositories invitationRepositories;

    public List<InvitationDto> getPendingInvitations() {
        User receiver = userRepository.findByEmail(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Podany użytkownik nie istnieje"));

        return invitationRepository.findByReceiverAndStatus(receiver, InvitationStatus.PENDING)
                .stream()
                .map(InvitationDtoMapper::map)
                .toList();
    }

    public void sendInvitation(Long receiverId) {
        Optional<User> sender = userRepository.findByEmail(SecurityUtils.getCurrentUserEmail());
        Optional<User> receiver = userRepository.findById(receiverId);
        if (sender.isEmpty() || receiver.isEmpty()) {
            throw new ResourceNotFoundException("Nie znaleziono użytkownika");
        }

        // start L1 Builder - second usage
        Invitation invitation = new InvitationBuilder()
                .sender(sender.get())
                .receiver(receiver.get())
                .status(InvitationStatus.PENDING)
                .build();
        invitationRepository.save(invitation);
    }

    @Transactional
    public void acceptInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono zaproszenia"));

        invitation.getState().accept(invitation, invitationRepositories);

        // L3 Visitor - third usage
        InvitationStateVisitor visitor = new LoggingInvitationVisitor();
        invitation.getState().acceptVisitor(visitor, invitation);
    }

    public void rejectInvitation(Long invitationId) {
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono zaproszenia"));

        invitation.getState().reject(invitation, invitationRepository);

        InvitationStateVisitor visitor = new LoggingInvitationVisitor();
        invitation.getState().acceptVisitor(visitor, invitation);
    }

    public List<InvitationDto> getSentInvitations() {
        User user = userRepository.findByEmail(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Podany użytkownik nie istnieje"));
        List<Invitation> sentInvitations = invitationRepository.findBySenderAndStatus(user, InvitationStatus.PENDING);

        return sentInvitations.stream()
                .map(InvitationDtoMapper::map)
                .toList();

    }
}
