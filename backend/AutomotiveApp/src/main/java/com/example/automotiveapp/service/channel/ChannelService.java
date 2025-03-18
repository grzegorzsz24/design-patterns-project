package com.example.automotiveapp.service.channel;

import com.example.automotiveapp.dto.ChannelDto;

import java.util.List;

// L5 Dependency Inversion - sixth impl
public interface ChannelService {

    Long getChannelId(Long senderId, Long receiverId);
    List<ChannelDto> findUserChats();
}
