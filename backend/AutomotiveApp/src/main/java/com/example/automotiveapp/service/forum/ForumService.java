package com.example.automotiveapp.service.forum;

import com.example.automotiveapp.domain.forum.Forum;
import com.example.automotiveapp.domain.forum.ForumCollection;
import com.example.automotiveapp.dto.ForumDto;
import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.ForumDtoMapper;
import com.example.automotiveapp.reponse.ForumResponse;
import com.example.automotiveapp.repository.ForumRepository;
import com.example.automotiveapp.repository.SavedForumRepository;
import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.service.ContentFeed;
import com.example.automotiveapp.service.report.ReportMediator;
import com.example.automotiveapp.service.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumRepository forumRepository;
    private final ForumDtoMapper forumDtoMapper;
    private final UserRepository userRepository;
    private final SavedForumRepository savedForumRepository;
    private final ReportMediator reportMediator;

    public ForumDto saveForum(ForumDto forumDto) {
        Forum forum = forumDtoMapper.map(forumDto);
        Forum savedForum = forumRepository.save(forum);
        return ForumDtoMapper.map(savedForum);
    }

    // L3 Iterator - second usage
    public List<ForumDto> findForumsByUserNickname(String nickname) {
        userRepository.findByNicknameIgnoreCase(nickname)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono użytkownika"));

        List<Forum> forums = forumRepository.findAllByUser_NicknameIgnoreCaseOrderByCreatedAtDesc(nickname);
        ForumCollection forumCollection = new ForumCollection(forums);
        Iterator<Forum> forumIterator = forumCollection.createIterator();

        List<ForumDto> result = new ArrayList<>();
        while (forumIterator.hasNext()) {
            Forum forum = forumIterator.next();
            ForumDto forumDto = ForumDtoMapper.map(forum);
            forumDto.setSaved(savedForumRepository
                    .findByUserEmailAndForum_Id(SecurityUtils.getCurrentUserEmail(), forum.getId())
                    .isPresent());
            result.add(forumDto);
        }
        return result;
    }


    public ForumResponse findAllByFilters(String title, String carBrand, String carModel, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Long totalResults = forumRepository.countByTitleAndCarBrandAndCarModel(title, carBrand, carModel);
        List<Forum> forumList = forumRepository.findAllByTitleAndCarBrandAndCarModelOrderByCreatedAtDesc(title, carBrand, carModel, pageable);

        ContentFeed compositeFeed = new ContentFeed();
        for (Forum forum : forumList) {
            compositeFeed.add(new ForumContent(forum));
        }

        // L3 Visitor - second usage
        ForumDtoCollectorVisitor visitor = new ForumDtoCollectorVisitor(savedForumRepository);

        compositeFeed.accept(visitor);

        List<ForumDto> forumDtos = visitor.getForumDtos();

        return new ForumResponse(forumDtos, totalResults);
    }


    public ForumDto findForumById(Long forumId) {
        Forum forum = forumRepository.findById(forumId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono forum"));
        ForumDto forumDto = ForumDtoMapper.map(forum);
        forumDto.setSaved(savedForumRepository.findByUserEmailAndForum_Id(SecurityUtils.getCurrentUserEmail(), forum.getId()).isPresent());
        return forumDto;
    }

    public ReportDto reportForum(ReportDto reportDto) {
        return reportMediator.reportForum(reportDto);
    }

    public void deleteForumById(Long forumId) {
        forumRepository.deleteById(forumId);
    }
}
