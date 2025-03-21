package com.example.automotiveapp.service;

import com.example.automotiveapp.domain.forum.Forum;
import com.example.automotiveapp.domain.forum.SavedForum;
import com.example.automotiveapp.dto.ForumDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.ForumDtoMapper;
import com.example.automotiveapp.repository.SavedForumRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// L4 Single responsibility - second impl
@Service
@RequiredArgsConstructor
public class FindSavedForumsService {
    private final UserRepository userRepository;
    private final SavedForumRepository savedForumRepository;


    public List<ForumDto> findSavedForums(int page, int size) {
        Long userId = userRepository.findByEmail(SecurityUtils.getCurrentUserEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika")).getId();

        Pageable pageable = PageRequest.of(page - 1, size);
        List<SavedForum> savedForums = savedForumRepository.findAllByUserId(userId, pageable);
        List<Forum> forums = new ArrayList<>();
        List<ForumDto> forumDtos = new ArrayList<>();
        savedForums.forEach(val -> forums.add(val.getForum()));
        for (Forum forum : forums) {
            ForumDto forumDto = ForumDtoMapper.map(forum);
            forumDto.setSaved(savedForumRepository.findByUserEmailAndForum_Id(SecurityUtils.getCurrentUserEmail(), forum.getId()).isPresent());
            forumDtos.add(forumDto);
        }
        return forumDtos;
    }
}
