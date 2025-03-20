package com.example.automotiveapp.service.invitation;

import com.example.automotiveapp.domain.InvitationStatus;
import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.domain.invitation.Invitation;
import com.example.automotiveapp.domain.invitation.InvitationState;
import com.example.automotiveapp.dto.InvitationDto;
import com.example.automotiveapp.mapper.InvitationDtoMapper;
import com.example.automotiveapp.repository.InvitationRepositories;
import com.example.automotiveapp.repository.InvitationRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.utils.SecurityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvitationServiceTest {

    @Mock
    InvitationRepository invitationRepository;
    @Mock
    InvitationRepositories invitationRepositories;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    InvitationService invitationService;

    @Test
    @DisplayName("Should return list of pending InvitationDto when user is receiver")
    void should_ReturnPendingInvitationDtos_When_UserIsReceiver() {
        User user = createUser(1L, "test@example.com");
        Invitation invitation = new Invitation();
        invitation.setId(10L);
        InvitationDto dto = new InvitationDto();
        dto.setId(invitation.getId());

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(invitationRepository.findByReceiverAndStatus(user, InvitationStatus.PENDING)).thenReturn(List.of(invitation));

        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class);
             MockedStatic<InvitationDtoMapper> mapper = mockStatic(InvitationDtoMapper.class)) {

            mocked.when(SecurityUtils::getCurrentUserEmail).thenReturn(user.getEmail());
            mapper.when(() -> InvitationDtoMapper.map(invitation)).thenReturn(dto);

            List<InvitationDto> result = invitationService.getPendingInvitations();

            assertEquals(1, result.size());
            assertEquals(dto.getId(), result.get(0).getId());
        }
    }

    @Test
    @DisplayName("Should send invitation when both users exist")
    void should_SendInvitation_When_BothUsersExist() {
        User sender = createUser(1L, "sender@example.com");
        User receiver = createUser(2L, "receiver@example.com");

        when(userRepository.findByEmail(sender.getEmail())).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));

        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class)) {
            mocked.when(SecurityUtils::getCurrentUserEmail).thenReturn(sender.getEmail());

            invitationService.sendInvitation(receiver.getId());

            verify(invitationRepository, times(1)).save(any(Invitation.class));
        }
    }

    @Test
    @DisplayName("Should accept invitation when invitation exists")
    void should_AcceptInvitation_When_InvitationExists() {
        Invitation invitation = mock(Invitation.class);
        InvitationState state = mock(InvitationState.class);

        when(invitationRepository.findById(1L)).thenReturn(Optional.of(invitation));
        when(invitation.getState()).thenReturn(state);

        invitationService.acceptInvitation(1L);

        verify(state).accept(invitation, invitationRepositories);
        verify(state).acceptVisitor(any(InvitationStateVisitor.class), eq(invitation));
    }

    @Test
    @DisplayName("Should reject invitation when invitation exists")
    void should_RejectInvitation_When_InvitationExists() {
        Invitation invitation = mock(Invitation.class);
        InvitationState state = mock(InvitationState.class);

        when(invitationRepository.findById(1L)).thenReturn(Optional.of(invitation));
        when(invitation.getState()).thenReturn(state);

        invitationService.rejectInvitation(1L);

        verify(state).reject(invitation, invitationRepository);
        verify(state).acceptVisitor(any(InvitationStateVisitor.class), eq(invitation));
    }

    @Test
    @DisplayName("Should return list of sent InvitationDto when user is sender")
    void should_ReturnSentInvitations_When_UserIsSender() {
        User user = createUser(1L, "sender@example.com");
        Invitation invitation = new Invitation();
        invitation.setId(22L);
        InvitationDto dto = new InvitationDto();
        dto.setId(22L);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(invitationRepository.findBySenderAndStatus(user, InvitationStatus.PENDING)).thenReturn(List.of(invitation));

        try (MockedStatic<SecurityUtils> mocked = mockStatic(SecurityUtils.class);
             MockedStatic<InvitationDtoMapper> mapper = mockStatic(InvitationDtoMapper.class)) {

            mocked.when(SecurityUtils::getCurrentUserEmail).thenReturn(user.getEmail());
            mapper.when(() -> InvitationDtoMapper.map(invitation)).thenReturn(dto);

            List<InvitationDto> result = invitationService.getSentInvitations();

            assertEquals(1, result.size());
            assertEquals(dto.getId(), result.get(0).getId());
        }
    }

    private User createUser(Long id, String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        return user;
    }
}
