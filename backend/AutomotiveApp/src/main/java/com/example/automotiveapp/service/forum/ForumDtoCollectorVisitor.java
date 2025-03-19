package com.example.automotiveapp.service.forum;

import com.example.automotiveapp.domain.forum.Forum;
import com.example.automotiveapp.dto.ForumDto;
import com.example.automotiveapp.mapper.ForumDtoMapper;
import com.example.automotiveapp.repository.SavedForumRepository;
import com.example.automotiveapp.service.ContentFeed;
import com.example.automotiveapp.service.ContentVisitor;
import com.example.automotiveapp.service.post.PostContent;
import com.example.automotiveapp.service.utils.SecurityUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class ForumDtoCollectorVisitor implements ContentVisitor {
    private final SavedForumRepository savedForumRepository;
    private final List<ForumDto> forumDtos = new ArrayList<>();

    @Override
    public void visit(ContentFeed feed) {

    }

    @Override
    public void visit(ForumContent forumContent) {
        Forum forum = forumContent.getForum();

        ForumDto forumDto = ForumDtoMapper.map(forum);

        boolean isSaved = savedForumRepository
                .findByUserEmailAndForum_Id(SecurityUtils.getCurrentUserEmail(), forum.getId())
                .isPresent();

        forumDto.setSaved(isSaved);

        forumDtos.add(forumDto);
    }

    @Override
    public void visit(PostContent article) {

    }
}
