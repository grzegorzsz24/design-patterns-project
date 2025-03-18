package com.example.automotiveapp.service.channel;

import com.example.automotiveapp.domain.Channel;
import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.dto.ChannelDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.ChannelDtoMapper;
import com.example.automotiveapp.repository.ChannelRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    @Override
    public Long getChannelId(Long senderId, Long receiverId) {
        return channelRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(senderId, receiverId)
                .map(Channel::getId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono kanału"));
    }

    @Override
    public List<ChannelDto> findUserChats() {
        User user = userRepository.findByEmail(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));
        return channelRepository.findAllBySenderIdOrReceiverId(user.getId(), user.getId())
                .stream()
                .map(ChannelDtoMapper::map)
                .toList();
    }
}
