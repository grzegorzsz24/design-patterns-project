package com.example.automotiveapp.service;

import com.example.automotiveapp.domain.InvitationStatus;
import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.domain.friendship.Friendship;
import com.example.automotiveapp.domain.invitation.Invitation;
import com.example.automotiveapp.dto.UserDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.UserDtoMapper;
import com.example.automotiveapp.repository.ChannelRepository;
import com.example.automotiveapp.repository.FriendshipRepository;
import com.example.automotiveapp.repository.InvitationRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.utils.SecurityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {

    @Mock
    FriendshipRepository friendshipRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    InvitationRepository invitationRepository;
    @Mock
    ChannelRepository channelRepository;

    @InjectMocks
    FriendshipService friendshipService;

    @Test
    @DisplayName("Should return list of UserDto when user has friends")
    void should_ReturnUserDtos_When_UserHasFriends() {
        Long userId = 1L;
        User user = createUser(userId, "user@example.com");
        User friend = createUser(2L, "friend@example.com");

        List<Friendship> friendships = List.of(createFriendship(user, friend));
        UserDto friendDto = new UserDto();
        friendDto.setId(friend.getId());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(friendshipRepository.findByUser1OrUser2(user, user)).thenReturn(friendships);

        try (MockedStatic<UserDtoMapper> mockedMapper = Mockito.mockStatic(UserDtoMapper.class)) {
            mockedMapper.when(() -> UserDtoMapper.map(friend)).thenReturn(friendDto);

            List<UserDto> result = friendshipService.getUserFriends(userId);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(friend.getId(), result.get(0).getId());
        }
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user not found in getUserFriends")
    void should_ThrowResourceNotFoundException_When_UserNotFound_GetUserFriends() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> friendshipService.getUserFriends(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Should save friendship when both users exist in addFriend")
    void should_SaveFriendship_When_BothUsersExist_AddFriend() {
        Long user2Id = 2L;
        String currentUserEmail = "user1@example.com";
        User user1 = createUser(1L, currentUserEmail);
        User user2 = createUser(user2Id, "friend@example.com");

        when(userRepository.findByEmail(currentUserEmail)).thenReturn(Optional.of(user1));
        when(userRepository.findById(user2Id)).thenReturn(Optional.of(user2));

        try (MockedStatic<SecurityUtils> securityUtils = Mockito.mockStatic(SecurityUtils.class)) {
            mockCurrentUserEmail(currentUserEmail, securityUtils);

            friendshipService.addFriend(user2Id);

            verify(friendshipRepository, times(1)).save(any(Friendship.class));
        }
    }

    @Test
    @DisplayName("Should delete friendship and channel when friendship exists in removeFriend")
    void should_DeleteFriendshipAndChannel_When_FriendshipExists_RemoveFriend() {
        Long user2Id = 2L;
        String currentUserEmail = "user1@example.com";
        User user1 = createUser(1L, currentUserEmail);
        User user2 = createUser(user2Id, "friend@example.com");

        Friendship friendship = createFriendship(user1, user2);

        when(userRepository.findByEmail(currentUserEmail)).thenReturn(Optional.of(user1));
        when(userRepository.findById(user2Id)).thenReturn(Optional.of(user2));
        when(friendshipRepository.findByUser1AndUser2(user1, user2)).thenReturn(Optional.of(friendship));
        when(friendshipRepository.findByUser1AndUser2(user2, user1)).thenReturn(Optional.empty());

        try (MockedStatic<SecurityUtils> securityUtils = Mockito.mockStatic(SecurityUtils.class)) {
            mockCurrentUserEmail(currentUserEmail, securityUtils);

            friendshipService.removeFriend(user2Id);

            verify(friendshipRepository, times(1)).delete(friendship);
            verify(channelRepository, times(1)).deleteChannelBySenderAndReceiverIds(user1.getId(), user2.getId());
        }
    }

    @Test
    @DisplayName("Should return list of UserDto of non-friends when finding not friends")
    void should_ReturnUserDtos_When_FindingNotFriends() {
        String currentUserEmail = "user1@example.com";
        User loggedUser = createUser(1L, currentUserEmail);
        User friend = createUser(2L, "friend@example.com");
        User invitedUser = createUser(3L, "invited@example.com");
        User notRelatedUser = createUser(4L, "stranger@example.com");

        Invitation sentInvitation = createPendingInvitation(loggedUser, invitedUser);

        when(userRepository.findByEmail(currentUserEmail)).thenReturn(Optional.of(loggedUser));
        when(friendshipRepository.findByUser1OrUser2(loggedUser, loggedUser))
                .thenReturn(List.of(createFriendship(loggedUser, friend)));
        when(invitationRepository.findInvitationsBySenderAndStatus(loggedUser, InvitationStatus.PENDING))
                .thenReturn(List.of(sentInvitation));
        when(invitationRepository.findInvitationsByReceiverAndStatus(loggedUser, InvitationStatus.PENDING))
                .thenReturn(List.of());
        when(userRepository.findAll())
                .thenReturn(List.of(loggedUser, friend, invitedUser, notRelatedUser));

        UserDto expectedDto = new UserDto();
        expectedDto.setId(notRelatedUser.getId());

        try (
                MockedStatic<SecurityUtils> securityUtils = Mockito.mockStatic(SecurityUtils.class);
                MockedStatic<UserDtoMapper> mapper = Mockito.mockStatic(UserDtoMapper.class)
        ) {
            mockCurrentUserEmail(currentUserEmail, securityUtils);
            mapper.when(() -> UserDtoMapper.map(notRelatedUser)).thenReturn(expectedDto);

            List<UserDto> result = friendshipService.findNotFriends();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(notRelatedUser.getId(), result.get(0).getId());
        }
    }

    private User createUser(Long id, String email) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        return user;
    }

    private Friendship createFriendship(User user1, User user2) {
        Friendship friendship = new Friendship();
        friendship.setUser1(user1);
        friendship.setUser2(user2);
        return friendship;
    }

    private Invitation createPendingInvitation(User sender, User receiver) {
        Invitation invitation = new Invitation();
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setStatus(InvitationStatus.PENDING);
        return invitation;
    }

    private void mockCurrentUserEmail(String email, MockedStatic<SecurityUtils> securityUtils) {
        securityUtils.when(SecurityUtils::getCurrentUserEmail).thenReturn(email);
    }
}
